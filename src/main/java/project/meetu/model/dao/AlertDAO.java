package project.meetu.model.dao;

import java.util.List;

import project.meetu.model.dto.Alert;

public interface AlertDAO {

	// 새로운 알림 개수 조회
	int findNewAlertCount(String userId);
	
	// 새로운 알림 목록 조회
	List<Alert> findnewAlertList(String userId);
	
	// 읽음여부가 0인 알림 목록의 읽음여부를 1로 변경
	boolean changeRead(String userId);
	
	// 새로운 알림 생성
	boolean createAlert(Alert alert);
	
	// 읽음여부가 1인 알림 목록 삭제
	boolean deleteReadAlert(String userId);
	
}
