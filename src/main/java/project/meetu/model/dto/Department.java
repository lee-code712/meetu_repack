package project.meetu.model.dto;

public class Department {
	private String deptNo;
	private String deptName;
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
	public College getCollegeInfo() {
		return collegeInfo;
	}
	public void setCollegeInfo(College collegeInfo) {
		this.collegeInfo = collegeInfo;
	}
	
}
