package project.meetu.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.meetu.model.dao.ConsultDAO;
import project.meetu.model.dto.College;
import project.meetu.model.dto.Consult;
import project.meetu.model.dto.Department;
import project.meetu.model.dto.Professor;

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
				if (consult.getState() != 1 && consult.getState() != 3) {
					consultList.remove(consult);
				}
			}
		}
		
		return consultList;
	}
}
