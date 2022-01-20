package project.meetu.model.service;
/* 메소드명이 좀 더 비즈니스에 가까움 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.meetu.model.dto.ServiceUser;
import project.meetu.model.service.exception.LoginException;
import project.meetu.model.dao.UserDAO;

@Service
public class UserManager {

	private final UserDAO userDao;
	
	// 외부에서 가져와 값을 넣어주는 경우 - dependency injection(의존성 주입)
	@Autowired
	public UserManager(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	/* 로그인 */
	public ServiceUser login(ServiceUser user) throws LoginException {
		
		ServiceUser findUser = userDao.findUser(user.getUserId());

		if (findUser == null) {
			throw new LoginException("존재하지 않는 회원입니다.");
		}
		else if (user.getMemberInfo().getRole() != findUser.getMemberInfo().getRole()) {
			throw new LoginException("존재하지 않는 회원입니다.");
		}
		else if (!user.getPassword().equals(findUser.getPassword())) {
			throw new LoginException("비밀번호가 일치하지 않습니다.");
		}
		
		return findUser;
	}
	
}
