package project.meetu.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.meetu.model.dao.mapper.AlertMapper;
import project.meetu.model.dto.Alert;

@Component
public class AlertDAOImpl implements AlertDAO {
	
	@Autowired
	private AlertMapper alertMapper;

	// 새로운 알림 개수 조회
	@Override
	public int findNewAlertCount(String userId) {
		return alertMapper.selectAlertCountByIsRead(userId);
	}

	// 새로운 알림 목록 조회
	@Override
	public List<Alert> findnewAlertList(String userId) {
		return alertMapper.selectAlertByIsRead(userId);
	}

	// 읽음여부가 0인 알림 목록의 읽음여부를 1로 변경
	@Override
	public boolean changeRead(String userId) {
		int ck = alertMapper.updateIsRead(userId);
		if (ck > 0) return true;
		return false;
	}

	// 읽음여부가 1인 알림 목록 삭제
	@Override
	public boolean deleteReadAlert(String userId) {
		int ck = alertMapper.deleteAlertByIsRead(userId);
		if (ck > 0) return true;
		return false;
	}

	// 새로운 알림 생성
	@Override
	public boolean createAlert(Alert alert) {
		int ck = alertMapper.insertAlert(alert);
		if (ck > 0) return true;
		return false;
	}

	// 상담 id를 이용해 새로운 알림 생성
	@Override
	public boolean createAlertByConsultId(Alert alert, int role, int consultId) {
		int ck = alertMapper.insertAlertByConsultId(alert, role, consultId);
		if (ck > 0) return true;
		return false;
	}

}
