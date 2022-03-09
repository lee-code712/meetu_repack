package project.meetu.model.dao;

import java.util.List;

import project.meetu.model.dto.Consult;
import project.meetu.model.dto.ConsultableTime;

public interface ConsultDAO {

	// 회원의 전체 상담목록 조회
	List<Consult> findConsultList(String userId);
	
	// 상담id에 대한 상세정보 조회
	Consult findReservation(String consultId);
	
	// 예약 상태 변경
	boolean changeStatus(Consult reservation);

	// 특정 학생-교수 간 예약 레코드 존재 여부
	boolean checkDuplicatedConsultion(String stuId, String profId);

	// 특정 교수의 상담 가능 시간 반환
	List<ConsultableTime> findConsultableTimeList(String profId);

	// 선택한 시간대에 예약 내역이 존재하는지 확인
	boolean checkDuplicatedConsultDate(String stuId, String startDate, String endDate);

	// 예약 생성
	boolean makeReservation(Consult consult);
	
	// 상담 백업 생성
	boolean createConsultBackup(String consultId);
	
	// 상담 내용 생성
	boolean createConsultRecord(String consultId);
	
	// 상담 내용 변경
	boolean changeConsultRecord(Consult consult);
	
	// 상담 백업의 상담 내용 변경
	boolean changeConsultBackupContent(Consult consult);
	
	// 예약 변경
	boolean updateReservation(Consult consult);
}
