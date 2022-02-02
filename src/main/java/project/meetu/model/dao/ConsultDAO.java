package project.meetu.model.dao;

import java.util.List;

import project.meetu.model.dto.Consult;

public interface ConsultDAO {

	// 회원의 전체 상담목록 조회
	List<Consult> findConsultList(String userId);
	
}
