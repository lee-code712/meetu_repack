package project.meetu.model.dto;

public class ConsultableTime {
	private int ableDate;
	private String ableTime;
	private String profId;
	
	public ConsultableTime() {
		super();
	}

	public ConsultableTime(int ableDate, String ableTime, String profId) {
		super();
		this.ableDate = ableDate;
		this.ableTime = ableTime;
		this.profId = profId;
	}
	
	public int getAbleDate() {
		return ableDate;
	}
	public void setAbleDate(int ableDate) {
		this.ableDate = ableDate;
	}
	public String getAbleTime() {
		return ableTime;
	}
	public void setAbleTime(String ableTime) {
		this.ableTime = ableTime;
	}
	public String getProfId() {
		return profId;
	}
	public void setProfId(String profId) {
		this.profId = profId;
	}
	
	
}
