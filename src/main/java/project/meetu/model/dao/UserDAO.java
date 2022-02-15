/* 메소드명이 좀 더 직관적이고 단순함 */
package project.meetu.model.dao;

import java.util.List;

import project.meetu.model.dto.College;
import project.meetu.model.dto.Course;
import project.meetu.model.dto.Department;
import project.meetu.model.dto.Member;
import project.meetu.model.dto.Professor;
import project.meetu.model.dto.ServiceUser;

public interface UserDAO {
	
	// 회원 조회
	ServiceUser findUser(String userId);
	
	// memberNo에 해당하는 회원 조회
	ServiceUser findUserByMemberNo(String memberNo);
	
	// 학생 구성원 정보 조회
	Member findStudentMember(String userId);
	
	// 교수 구성원 정보 조회
	Member findProfessorMember(String userId);
	
	// 전체 단대 조회
	List<College> findCollegeList();
		
	// 전체 학과 조회
	List<Department> findDepartmentList();
	
	// 학과에 따른 과목 조회
	List<Course> findCourseListByDept(String userId);

	// 학과별 교수 조회
	List<Professor> findDeptProfessorList(String deptNo);
	
	// 교수 검색
	List<Professor> findProfessorListByKeyword(String keyword);
	
	// memberNo에 해당하는 교수 검색
	Professor findProfessorByMemberNo(String memberNo);
	
	// 이메일 변경
	boolean changeEmail(String value, String userId);
	
	// 전공 변경
	boolean changeMajor(String value, String userId);
	
	// 과목 추가
	boolean createClass(String courseNo, String userId);
	
	// 과목 삭제
	boolean deleteClass(String courseNo, String userId);
}
