package project.meetu.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.meetu.model.dto.ServiceUser;
import project.meetu.model.service.UserManager;

@Controller
public class JoinController {
	
	private final UserManager userService;
	
	@Autowired // 컨테이너에 있는 서비스와 연결됨
	public JoinController(UserManager userService) {
		this.userService = userService;
	}

	@GetMapping("/user/joinForm")
	public String join() {
		return "user/join";
	}

	@PostMapping("/user/join") // POST방식으로 데이터 전달 시 사용
	public String join(ServiceUser serviceUser, RedirectAttributes rttr, Model model) {
		// 유효한 회원가입 요청인지 확인
		boolean success = userService.registerCheck(serviceUser);
		
		if (!success) {
			rttr.addFlashAttribute("registerFailed", true);
			return "redirect:/";
		}
		
		// 유효한 요청이라면, 확인 메일 전송
		model.addAttribute("serviceUser", serviceUser);
		return "user/joinAuth";
	}
	
	@GetMapping("/user/joinAuthForm")
	public String joinAuth() {
		return "user/joinAuth";
	}
	
	@PostMapping("/user/joinAuth") 
	public String joinAuth(ServiceUser serviceUser, RedirectAttributes rttr) {
		boolean success = userService.register(serviceUser);
		
		if (!success) {
			rttr.addFlashAttribute("registerFailed", true);
		}
		
		return "redirect:/";
	}
}
