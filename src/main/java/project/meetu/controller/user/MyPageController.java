package project.meetu.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.meetu.model.dto.Consult;
import project.meetu.model.dto.Course;
import project.meetu.model.dto.Member;
import project.meetu.model.service.AlertManager;
import project.meetu.model.service.ConsultManager;
import project.meetu.model.service.UserManager;
import project.meetu.model.service.exception.ChangePwdException;
import project.meetu.model.service.exception.ResignException;

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
	
	@PostMapping("/user/my/changePwd")
	public String changePwd(HttpServletRequest req, RedirectAttributes rttr) {
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		String oldPwd = (String) req.getParameter("oldPwd");
		String newPwd = (String) req.getParameter("newPwd");
		String newPwdCk = (String) req.getParameter("newPwdCk");
		
		try {
			userService.changePwd(userId, oldPwd, newPwd, newPwdCk);
			
			return "redirect:/user/logout?changePwdSuccess=1";
			
		} catch (ChangePwdException e) {
			e.printStackTrace(); // exception console에서 확인
			
			rttr.addFlashAttribute("changePwdFailed", true); // redirect시 값 전달
			rttr.addFlashAttribute("exception", e.getMessage());
			return "redirect:/user/my/pwd";
		}
	
	}
	
	@GetMapping("/user/my/resignForm")
	public String goResignForm() {
		return "user/resignForm";
	}
	
	@PostMapping("/user/resign")
	public String resign(HttpServletRequest req, RedirectAttributes rttr) {
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		String oldPwd = (String) req.getParameter("oldPwd");
		
		try {
			userService.resign(userId, oldPwd);
			
			return "redirect:/user/logout?resignSuccess=1";
			
		} catch (ResignException e) {
			e.printStackTrace(); // exception console에서 확인
			
			rttr.addFlashAttribute("resignFailed", true); // redirect시 값 전달
			rttr.addFlashAttribute("exception", e.getMessage());
			return "redirect:/user/my/resignForm";
		}
		
	}
}
