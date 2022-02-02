package project.meetu.model.dto;

public class Member {
	private String memberNo;
	private int role;
	private String name;
	private String email;
	private String deptNo;
	private Professor profInfo;
	private Student stuInfo;
	
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	public Professor getProfInfo() {
		return profInfo;
	}
	public void setProfInfo(Professor profInfo) {
		this.profInfo = profInfo;
	}
	public Student getStuInfo() {
		return stuInfo;
	}
	public void setStuInfo(Student stuInfo) {
		this.stuInfo = stuInfo;
	}
	
}
