package project.meetu.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import project.meetu.model.service.UserManager;

@Controller
public class MyPageController {

	private final UserManager userService;
	
	@Autowired
	public MyPageController(UserManager userService) {
		this.userService = userService;
	}
	
	@GetMapping("/user/my")
	public String goMyPage() {
		return "user/myPage";
	}
}
