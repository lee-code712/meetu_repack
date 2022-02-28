package project.meetu.controller.message;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {
	
	@GetMapping("/message")
	public String goMessagePage() {
		return "message/messageView";
	}

}
