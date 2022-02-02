package project.meetu.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import project.meetu.model.dao.ConsultDAO;
import project.meetu.model.dto.Consult;

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
}
