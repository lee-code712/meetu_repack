package project.meetu.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.meetu.model.dao.ConsultDAO;
import project.meetu.model.dto.Consult;

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
}
