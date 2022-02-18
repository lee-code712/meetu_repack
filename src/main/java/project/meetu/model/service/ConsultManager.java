package project.meetu.model.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.meetu.model.dao.ConsultDAO;
import project.meetu.model.dto.Consult;
import project.meetu.model.dto.ConsultableTime;
import project.meetu.model.dto.ServiceUser;

@Service
public class ConsultManager {

	private final ConsultDAO consultDao;
	
	@Autowired
	public ConsultManager(ConsultDAO consultDao) {
		this.consultDao = consultDao;
	}
	
	/* 회원의 전체 상담목록 조회 */
	public List<Consult> getUserConsults(String userId) {
		return consultDao.findConsultList(userId);
	}
	
	/* 회원의 스케줄목록 조회 */
	public List<Consult> getUserSchedules(String userId) {
		List<Consult> consultList = consultDao.findConsultList(userId);
		List<Consult> resultList = new ArrayList<Consult>();
		
		if (consultList != null) {
			for (Consult consult : consultList) {
				if (consult.getStatus() == 1 || consult.getStatus() == 3) {
					resultList.add(consult);
				}
			}
		}
		
		return resultList;
	}
	
	/* 상담id에 대한 상세정보 조회 */
	public Consult getReservationInfo(String consultId) {
		return consultDao.findReservation(consultId);
	}
	
	/* 예약 상태 변경 */
	public boolean changeReservationStatus(Consult reservation) {
		if (reservation.getStatus() == 3) {
			String consultId = String.valueOf(reservation.getId());

			boolean success = consultDao.changeStatus(reservation);
			if (!success) return false;
			success = consultDao.createConsultRecord(consultId);
			if (!success) return false;
			return consultDao.createConsultBackup(consultId); 
		}
		else {
			return consultDao.changeStatus(reservation);
		}
	}
	
	/* 특정 학생-교수 간 예약 레코드 존재 여부 */
	public boolean checkReservated(String stuId, String profId) {
		return consultDao.checkDuplicatedConsultion(stuId, profId);
	}

	/* 특정 교수의 상담 가능 시간 반환 */
	public List<ConsultableTime> getConsultableTimes(String profId) {
		return consultDao.findConsultableTimeList(profId);
	}

	/* 특정 교수의 예약 대기, 예약 확정 상담 반환 */
	public List<Consult> getUndoneReservation(String profId) {
		List<Consult> consultList = consultDao.findConsultList(profId);
		List<Consult> resultList = new ArrayList<Consult>();
		
		if (consultList != null) {
			for (Consult consult : consultList) {
				if (consult.getStatus() == 0 || consult.getStatus() == 1) {
					resultList.add(consult);
				}
			}
		}
		return resultList;
	}

	public int makeReservation(String choiceDate, String startTime, String consultTime, String typeBtn, String radio, String profId, String stuId) {
		// 예약 일시 생성 - 21/07/28 09:00:00의 형식
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		year = year.substring(2, 4);

		String start_date = choiceDate + " " + startTime + ":00";

		// end_time 생성 - 21/07/28 09:00:00의 형식
		cal = Calendar.getInstance();
		year = Integer.toString(cal.get(Calendar.YEAR));

		String[] startTimeArr = startTime.split(":"); // 09:00을 09, 00으로 분리
		int start_timeInt = Integer.parseInt(startTimeArr[0]); // 09만 이용
		int consultTimeInt = Integer.parseInt(consultTime.replaceAll("[^0-9]", ""));
		int endTimeInt = start_timeInt + consultTimeInt;

		String endTime = "";
		if (endTimeInt < 10) { // 시간이 한 자리 수인 경우 0을 붙여서 09와 같이 두 자리 수로 만들어 주어야 함
			endTime = "0" + Integer.toString(start_timeInt + consultTimeInt);
		} else {
			endTime = Integer.toString(start_timeInt + consultTimeInt);
		}
		String end_date = choiceDate + " " + endTime + ":00:00";

		// 예약 DTO 생성
		Consult consult = new Consult();
		consult.setStartDate(start_date);
		consult.setEndDate(end_date);

		if (radio.equals("1")) { // 상담 이유
			consult.setReason("전담 교수 상담");
		} else if (radio.equals("2")) {
			consult.setReason("진로 상담");
		} else if (radio.equals("3")) {
			consult.setReason("휴학 상담");
		} else if (radio.equals("4")) {
			consult.setReason("대학원 상담");
		} else {
			consult.setReason(radio);
		}

		if (typeBtn.equals("오프라인")) { // 온라인/오프라인 상담 구분. 오프라인 0, 온라인 1
			consult.setType(0);
		} else {
			consult.setType(1);
		}

		ServiceUser profUser = new ServiceUser();
		profUser.setUserId(profId);
		consult.setProfUser(profUser);

		ServiceUser stuUser = new ServiceUser();
		stuUser.setUserId(stuId);
		consult.setStuUser(stuUser);

		// 선택한 시간대에 예약 내역이 존재하는지 확인
		boolean date_check = consultDao.checkDuplicatedConsultDate(consult.getStuUser().getUserId(), consult.getStartDate(), consult.getEndDate());
		if (!date_check) {
			return 2;
		}

		// 예약 추가
		boolean is_added = consultDao.makeReservation(consult);
		if (!is_added) {
			return 3;
		}
		
		return 1;
	}
	
	/* 상담 내용 수정 */
	public boolean changeConsultContent(Consult consult) {
		return consultDao.changeConsultRecord(consult);
	}
	
}
