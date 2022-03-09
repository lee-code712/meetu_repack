package project.meetu.model.dto;

// 예약 정보를 전달하기 위한 dto (db와 상관없음)
public class Reservation {
	private String choiceDate;
	private String startTime;
	private String consultTime;
	private String type;
	private String radio;
	private String profId;
	
	public String getChoiceDate() {
		return choiceDate;
	}
	public void setChoiceDate(String choiceDate) {
		this.choiceDate = choiceDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getConsultTime() {
		return consultTime;
	}
	public void setConsultTime(String consultTime) {
		this.consultTime = consultTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	public String getProfId() {
		return profId;
	}
	public void setProfId(String profId) {
		this.profId = profId;
	}
	
}
