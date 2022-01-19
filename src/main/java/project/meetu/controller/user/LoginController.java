package project.meetu.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.meetu.model.dto.Member;
import project.meetu.model.dto.ServiceUser;
import project.meetu.model.service.UserManager;

@Controller
public class LoginController {
	
	private final UserManager userService;
	
	@Autowired // 컨테이너에 있는 서비스와 연결됨
	public LoginController(UserManager userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String index() {
		return "user/loginForm";
	}
	
	@PostMapping("user/login") // POST방식으로 데이터 전달 시 사용 - 보통 데이터 등록 시 사용하는 방식
	public String login(@RequestParam("role") String role, ServiceUser user, Model model) {
		user.setMemberInfo(new Member());
		user.getMemberInfo().setRole(Integer.parseInt(role));	
		
		user = userService.login(user);

		if (user != null) {
			model.addAttribute("user", user);
			return "user/loginSuccess";
		}
		return "redirect:/";
	}
}
