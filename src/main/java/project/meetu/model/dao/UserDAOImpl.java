package project.meetu.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.meetu.model.dto.College;
import project.meetu.model.dto.ConsultableTime;
import project.meetu.model.dto.Course;
import project.meetu.model.dto.Department;
import project.meetu.model.dto.Member;
import project.meetu.model.dto.Office;
import project.meetu.model.dto.Professor;
import project.meetu.model.dto.ServiceUser;
import project.meetu.model.dao.mapper.UserMapper;

@Component
public class UserDAOImpl implements UserDAO {
	
	@Autowired
    private UserMapper userMapper;

	// 회원 조회
	@Override
	public ServiceUser findUser(String userId) {
		return userMapper.selectServiceUser(userId);
	}
	
	// 학생 구성원 정보 조회
	@Override
	public Member findStudentMember(String userId) {
		return userMapper.selectStudentMember(userId);
	}

	// 교수 구성원 정보 조회
	@Override
	public Member findProfessorMember(String userId) {
		return userMapper.selectProfessorMember(userId);
	}
	
	// 전체 단대 조회
	@Override
	public List<College> findCollegeList() {
		return userMapper.selectCollege();
	}

	// 전체 학과 조회
	@Override
	public List<Department> findDepartmentList() {
		return userMapper.selectDepartment();
	}
	
	// 학과에 따른 과목 조회
	@Override
	public List<Course> findCourseListByDept(String userId) {
		return userMapper.selectCourseByDeptNo(userId);
	}

	// 학과별 교수 조회
	@Override
	public List<Professor> findDeptProfessorList(String deptNo) {
		return userMapper.selectProfessorByDept(deptNo);
	}

	// memberNo에 해당하는 회원 조회
	@Override
	public ServiceUser findUserByMemberNo(String memberNo) {
		return userMapper.selectServiceUserByMemberNo(memberNo);
	}

	// 교수 검색
	@Override
	public List<Professor> findProfessorListByKeyword(String keyword) {
		return userMapper.selectProfessorByKeyword(keyword);
	}

	// memberNo에 해당하는 교수 검색
	@Override
	public Professor findProfessorByMemberNo(String memberNo) {
		return userMapper.selectProfessorByMemberNo(memberNo);
	}

	// 이메일 변경
	@Override
	public boolean changeEmail(String value, String userId) {
		int ck = userMapper.updateEmail(value, userId);
		if (ck > 0) return true;
		return false;
	}

	// 전공 변경
	@Override
	public boolean changeMajor(String value, String userId) {
		int ck = userMapper.updateMajor(value, userId);
		if (ck > 0) return true;
		return false;
	}

	// 과목 추가
	@Override
	public boolean createClass(String courseNo, String userId) {
		int ck = userMapper.insertClass(courseNo, userId);
		if (ck > 0) return true;
		return false;
	}

	// 과목 삭제
	@Override
	public boolean deleteClass(String courseNo, String userId) {
		int ck = userMapper.deleteClass(courseNo, userId);
		if (ck > 0) return true;
		return false;
	}

	// 상담 가능 시간 추가
	@Override
	public boolean createConsultableTime(ConsultableTime consultableTime) {
		int ck = userMapper.insertConsultableTime(consultableTime);
		if (ck > 0) return true;
		return false;
	}

	// 상담 가능 시간 삭제
	@Override
	public boolean deleteConsultableTime(ConsultableTime consultableTime) {
		int ck = userMapper.deleteConsultableTime(consultableTime);
		if (ck > 0) return true;
		return false;
	}

	// 교수 연구실
	@Override
	public Office findOfficeByProfId(String memberNo) {
		return userMapper.selectOfficeByProfId(memberNo);
	}
	
	// 회원가입
	@Override
	public boolean createServiceUser(ServiceUser serviceUser) {
		int ck = userMapper.insertServiceUser(serviceUser);
		if (ck > 0) return true;
		return false;
	}

	// 비밀번호 변경
	@Override
	public boolean changePassword(String newPwd, String userId) {
		int ck = userMapper.updatePassword(newPwd, userId);
		if (ck > 0) return true;
		return false;
	}

	// 회원 탈퇴
	@Override
	public boolean deleteUser(String oldPwd, String userId) {
		int ck = userMapper.deleteServiceUser(oldPwd, userId);
		if (ck > 0) return true;
		return false;
	}
	
}
