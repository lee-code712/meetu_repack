package project.meetu.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import project.meetu.model.service.UserManager;

@Controller
public class UpdateMemberInfoController {
	
	private final UserManager userService;
	
	@Autowired
	public UpdateMemberInfoController(UserManager userService) {
		this.userService = userService;
	}
	
	@GetMapping("/user/my/changeInfo")
	public String changeInfo(String item, String value) {
		
		
		return "redirect:/user/my/Info";
		
	}
}
