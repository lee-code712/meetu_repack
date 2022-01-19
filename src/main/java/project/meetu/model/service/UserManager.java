package project.meetu.model.service;
/* 메소드명이 좀 더 비즈니스에 가까움 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.meetu.model.dto.ServiceUser;
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
	public ServiceUser login(ServiceUser user) {
		
		ServiceUser findUser = userDao.findUser(user.getUserId());

		if (findUser != null) {
			if (user.getPassword().equals(findUser.getPassword())
					&& user.getMemberInfo().getRole() == findUser.getMemberInfo().getRole()) {
				return findUser;
			}
		}	
		
		return null;
	}
	
}
