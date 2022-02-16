package project.meetu.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.meetu.model.dto.Consult;
import project.meetu.model.dto.Course;
import project.meetu.model.dto.Member;
import project.meetu.model.service.ConsultManager;
import project.meetu.model.service.UserManager;

@Controller
public class MyPageController {

	private final UserManager userService;
	private final ConsultManager consultService;
	
	@Autowired
	public MyPageController(ConsultManager consultService, UserManager userService) {
		this.consultService = consultService;
		this.userService = userService;
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
	
	@GetMapping("/user/my/info")
	public String goUserInfoView(HttpServletRequest req, Model model) {
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		int role = (int) session.getAttribute("role");
		
		Member memberInfo = userService.getMemberInfo(userId, role);
		if (memberInfo != null) {
			model.addAttribute("member", memberInfo);
		}
		
		if (role == 0) {
			List<Course> courses = userService.getCoursesByDept(userId);
			if(courses != null) {
				model.addAttribute("courses", courses);
			}
		}
		
		return "user/userInfoView";
	
	}
	
}
