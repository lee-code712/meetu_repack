/* 메소드명이 좀 더 직관적이고 단순함 */
package project.meetu.model.dao;

import java.util.List;

import project.meetu.model.dto.College;
import project.meetu.model.dto.Department;
import project.meetu.model.dto.Professor;
import project.meetu.model.dto.ServiceUser;

public interface UserDAO {
	
	// 회원 조회
	ServiceUser findUser(String userId);
	
	// memberNo에 해당하는 회원 조회
	ServiceUser findUserByMemberNo(String memberNo);
	
	// 전체 단대 조회
	List<College> findCollegeList();
		
	// 전체 학과 조회
	List<Department> findDepartmentList();

	// 학과별 교수 조회
	List<Professor> findDeptProfessorList(String deptNo);
	
	// 교수 검색
	List<Professor> findProfessorListByKeyword(String keyword);
}
