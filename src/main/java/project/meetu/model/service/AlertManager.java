package project.meetu.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.meetu.model.dao.AlertDAO;
import project.meetu.model.dto.Alert;
import project.meetu.model.dto.Consult;

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
 	
	/* 예약 상태 변경에 따른 새로운 알림 생성 */
	public boolean addAlertByReservationStatus(String name, int role, Consult reservation) {
		Alert alert = new Alert();
		
		int status = reservation.getStatus();
		if (status == 1) {
			alert.setTypeNo(1);
			alert.setAlertMsg(name + "님이 예약을 승인했습니다.");
		}
		else if (status == 2) {
			alert.setTypeNo(2);
			alert.setAlertMsg(name + "님이 예약을 반려했습니다.");
		}
		else if (status == 3) {
			alert.setTypeNo(3);
			alert.setAlertMsg(name + "님이 상담을 완료처리 했습니다.");
		}
		else if (status == 4) {
			alert.setTypeNo(4);
			alert.setAlertMsg(name + "님이 예약을 취소했습니다.");
		}
		
		return alertDao.createAlert(alert, role, reservation.getId());
	}

}
