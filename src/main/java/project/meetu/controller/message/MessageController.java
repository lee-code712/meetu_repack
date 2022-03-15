package project.meetu.controller.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import project.meetu.model.dto.ServiceUser;
import project.meetu.model.service.AlertManager;
import project.meetu.model.service.MessageManager;
import project.meetu.model.service.UserManager;

@Controller
public class MessageController {
	private final MessageManager messageService;
	private final UserManager userService;
	private final AlertManager alertService;
	
	@Autowired // 컨테이너에 있는 서비스와 연결됨
	public MessageController(MessageManager messageService, UserManager userService, AlertManager alertService) {
		this.messageService = messageService;
		this.userService = userService;
		this.alertService = alertService;
	}
	
	@GetMapping("/message")
	public String goMessagePage(HttpServletRequest req) {
		HttpSession session = req.getSession();
		
		String id = (String) session.getAttribute("id");
		String name = (String) session.getAttribute("name");
		String role = (String) session.getAttribute("role");
		
		ServiceUser serviceUser = userService.getUserByUserId(id);
		
		return "message/messageView";
	}

}
