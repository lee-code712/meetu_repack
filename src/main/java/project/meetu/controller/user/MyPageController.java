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
import project.meetu.model.service.AlertManager;
import project.meetu.model.service.ConsultManager;
import project.meetu.model.service.UserManager;

@Controller
public class MyPageController {

	private final UserManager userService;
	private final ConsultManager consultService;
	private final AlertManager alertService;
	
	@Autowired
	public MyPageController(ConsultManager consultService, UserManager userService,
			AlertManager alertService) {
		this.consultService = consultService;
		this.userService = userService;
		this.alertService = alertService;
	}
	
	@GetMapping("/user/my")
	public String goMyPage(HttpServletRequest req, Model model) {
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		
		List<Consult> consultList = consultService.getUserConsults(userId);
		if (consultList != null) {
			model.addAttribute("consults", consultList);
		}
		
		// 알림 개수 갱신
		int newAlertCount = alertService.getNewAlertCount(userId);
		session.setAttribute("newAlerts", newAlertCount);
		
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
		
		// 알림 개수 갱신
		int newAlertCount = alertService.getNewAlertCount(userId);
		session.setAttribute("newAlerts", newAlertCount);
		
		return "user/userInfoView";
	
	}
	
	@GetMapping("/user/my/pwd")
	public String goPwdChangeForm() {
		return "user/pwdChangeForm";
	}
	
	@GetMapping("/user/my/resignForm")
	public String goResignForm() {
		return "user/resignForm";
	}
}
