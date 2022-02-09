package project.meetu.model.dto;

import java.util.List;

public class Professor {
	private String memberNo;
	private String major;
	private String officeNo;
	private List<Course> courses;
	private Member memberInfo;
	private boolean isUser;
	
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getOfficeNo() {
		return officeNo;
	}
	public void setOfficeNo(String officeNo) {
		this.officeNo = officeNo;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public Member getMember() {
		return memberInfo;
	}
	public void setMember(Member memberInfo) {
		this.memberInfo = memberInfo;
	}
	public boolean isUser() {
		return isUser;
	}
	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}
	
	
}
