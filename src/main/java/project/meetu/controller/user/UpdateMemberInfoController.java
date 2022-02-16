package project.meetu.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.meetu.model.dto.ConsultableTime;
import project.meetu.model.service.UserManager;

@Controller
public class UpdateMemberInfoController {
	
	private final UserManager userService;
	
	@Autowired
	public UpdateMemberInfoController(UserManager userService) {
		this.userService = userService;
	}
	
	@GetMapping("/user/my/changeInfo")
	public String changeInfo(String item, String value, HttpServletRequest req, RedirectAttributes rttr) {
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		
		boolean success = userService.changeinfoByItem(item, value, userId);
		if (!success) {
			rttr.addFlashAttribute("changeFailed", true);
		}
		
		return "redirect:/user/my/info";
		
	}
	
	@GetMapping("/user/my/class")
	public String addOrRemoveClass(String type, String courseNo, HttpServletRequest req, RedirectAttributes rttr) {
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		
		boolean success = userService.addOrRemoveClass(type, courseNo, userId);
		if (!success) {
			rttr.addFlashAttribute("changeFailed", true);
		}
		
		return "redirect:/user/my/info";
		
	}
	
	@GetMapping("/user/my/consultableTime")
	public String addOrRemoveConsultableTime(String type, String ableDate, String ableTime, 
			HttpServletRequest req, RedirectAttributes rttr) {
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		
		ConsultableTime consultableTime = new ConsultableTime(Integer.parseInt(ableDate), ableTime, userId);
		boolean success = userService.addOrRemoveConsultableTime(type, consultableTime);
		if (!success) {
			rttr.addFlashAttribute("changeFailed", true);
		}
		
		return "redirect:/user/my/info";
		
	}
	
}
