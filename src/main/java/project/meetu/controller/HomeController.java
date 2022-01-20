package project.meetu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String moveToHome() {
		// 상담일정 가져오는 코드 구현
		// 새로운 알림 개수 가져오는 코드 구현
		return "home";
	}
	
}
