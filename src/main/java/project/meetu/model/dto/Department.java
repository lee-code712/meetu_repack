package project.meetu.model.dto;

public class Department {
	private String deptNo;
	private String deptName;
	private String collegeNo; // College로 바꾸면 더 간편해지지 않을까..?
	private College collegeInfo;
	
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getCollegeNo() {
		return collegeNo;
	}
	public void setCollegeNo(String collegeNo) {
		this.collegeNo = collegeNo;
	}
	public College getCollegeInfo() {
		return collegeInfo;
	}
	public void setCollegeInfo(College collegeInfo) {
		this.collegeInfo = collegeInfo;
	}
	
}
