package project.meetu.model.dao;

import java.util.List;

import project.meetu.model.dto.Consult;

public interface ConsultDAO {

	// 회원의 전체 상담목록 조회
	List<Consult> findConsultList(String userId);
	
	// 상담id에 대한 상세정보 조회
	Consult findReservation(String consultId);
	
	// 예약 상태 변경
	boolean changeStatus(Consult reservation);

	// 특정 학생-교수 간 예약 레코드 존재 여부
	boolean checkDuplicatedConsultion(String stuId, String profId);
}
