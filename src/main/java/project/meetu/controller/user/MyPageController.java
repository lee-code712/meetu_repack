package project.meetu.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.meetu.model.dto.Consult;
import project.meetu.model.service.ConsultManager;

@Controller
public class MyPageController {

	private final ConsultManager consultService;
	
	@Autowired
	public MyPageController(ConsultManager consultService) {
		this.consultService = consultService;
	}
	
	@GetMapping("/user/my")
	public String goMyPage(HttpServletRequest req, Model model) {
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		
		List<Consult> consultList = consultService.getUserConsults(userId);
		if (consultList != null) {
			model.addAttribute("consults", consultList);
		}
		
		return "user/myPage";
	}
}
