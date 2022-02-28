package project.meetu.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.meetu.model.dao.AlertDAO;
import project.meetu.model.dto.Alert;

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
	
	/* 읽지 않은 알림목록 조회 */
	public List<Alert> getNewAlerts(String userId) {
		List<Alert> newAlerts = alertDao.findnewAlertList(userId);
		if(newAlerts != null) {
			for(Alert alert : newAlerts) {
				int type = alert.getTypeNo();
				if((type >= 0 && type <= 4) || type == 6) {
					alert.setUrl("/user/my");
				}
				else if(type == 5) {
					alert.setUrl("/message");
				}
				else if(type == 7) {
					if(alert.getAlertMsg().length() == 40) {
						alert.setUrl("/user/my");
					}
					else {
						alert.setUrl("/home#target_cal");
					}
				}
			}
		}
		return newAlerts;
	}
	
	/* 읽음여부 변경 */
	public boolean changeReadState(String userId) {
		return alertDao.changeRead(userId);
	}
 	
	/* 읽은 알림목록 삭제 */

}
