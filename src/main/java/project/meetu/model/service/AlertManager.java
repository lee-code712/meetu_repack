package project.meetu.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.meetu.model.dao.AlertDAO;

@Service
public class AlertManager {

	private final AlertDAO alertDao;
	
	@Autowired
	public AlertManager(AlertDAO alertDao) {
		this.alertDao = alertDao;
	}
	
	/* 새로운 알림 개수 조회 */
	public int getNewAlertCount(String userId) {
		return alertDao.findNewAlertCount(userId);
	}
}
