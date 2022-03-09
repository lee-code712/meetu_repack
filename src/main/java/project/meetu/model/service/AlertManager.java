package project.meetu.model.service;

import java.util.Calendar;
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
					if(alert.getAlertMsg().length() > 30) {
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
	
	/* 예약에 대한 새로운 알림 생성 */
	public boolean addAlertByMakeReservation(String name, String userId) {
		String alertMsg = name + "님이 상담을 예약했습니다.";
		Alert alert = new Alert(alertMsg, userId, 0);
		return alertDao.createAlert(alert);
	}
	
	/* 예약 변경에 대한 새로운 알림 생성  */
	public boolean addAlertByUpdateReservation(String name, int role, String consultId) {
		Alert alert = new Alert();
		alert.setTypeNo(1);
		alert.setAlertMsg(name + "님이 예약정보를 수정했습니다.");
		
		return alertDao.createAlertByConsultId(alert, role, Integer.parseInt(consultId));
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
		
		return alertDao.createAlertByConsultId(alert, role, reservation.getId());
	}
	
	/* 상담 예정일에 대한 새로운 알림 생성 */
	public boolean addAlertByConsultDate(String userId, int role, List<Consult> consults) {
		boolean success = false;
		
		if (consults != null) {
			for (Consult consult : consults) {
				if (consult.getStatus() != 1)
					continue;
				
				String startDate = consult.getStartDate().substring(0, 10);
				String[] dateArr = startDate.split("-");
				int dday = getDDay(dateArr[0], dateArr[1], dateArr[2]);
				
				String name;
				if (role == 0) {
					name = consult.getStuUser().getMemberInfo().getName();
				}
				else {
					name = consult.getProfUser().getMemberInfo().getName();
				}
				
				String alertMsg;
				if(dday < 0) {
					alertMsg = name + "님과의 상담 예정일이 지났습니다. 상담취소 혹은 완료처리 바랍니다.";
				}
				else if(dday == 0) {
					alertMsg = name + "님과의 상담 예정일 D-Day";
				}
				else {
					alertMsg = name + "님과의 상담까지 D-" + dday;
				}
				
				Alert alert = new Alert(alertMsg, userId, 7);
				success = alertDao.createAlert(alert);
			}
		}
		return success;
	}
	
	/* 읽음여부가 1인 알림 목록 삭제 */
	public boolean removeReadAlert(String userId) {
		return alertDao.deleteReadAlert(userId);
	}

	// d-day 계산
	private int getDDay(String year, String month, String day) {
		try {
			Calendar today = Calendar.getInstance();
			Calendar dday = Calendar.getInstance();
			dday.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
				
			long lToday = today.getTimeInMillis() / (24*60*60*1000);
			long lDday = dday.getTimeInMillis() / (24*60*60*1000);
				
			long substract = lDday - lToday;
			return (int)substract;
		} catch (Exception e) {
			return -1;
		}
	}

}
