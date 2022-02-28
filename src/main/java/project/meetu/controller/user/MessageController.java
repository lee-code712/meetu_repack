package project.meetu.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {
	
	@GetMapping("/user/message")
	public String goMessagePage() {
		return "user/message";
	}

}
