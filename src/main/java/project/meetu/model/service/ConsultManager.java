package project.meetu.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.meetu.model.dao.ConsultDAO;
import project.meetu.model.dto.Consult;
import project.meetu.model.dto.ConsultableTime;

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
		
		if (consultList != null) {
			for (Consult consult : consultList) {
				if (consult.getStatus() != 1 && consult.getStatus() != 3) {
					consultList.remove(consult);
				}
			}
		}
		
		return consultList;
	}
	
	/* 상담id에 대한 상세정보 조회 */
	public Consult getReservationInfo(String consultId) {
		return consultDao.findReservation(consultId);
	}
	
	/* 예약 상태 변경 */
	public boolean changeReservationStatus(Consult reservation) {
		return consultDao.changeStatus(reservation);
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
}
