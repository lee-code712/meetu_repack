package project.meetu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.meetu.model.dto.Consult;
import project.meetu.model.service.AlertManager;
import project.meetu.model.service.ConsultManager;

@Controller 
public class HomeController { 
	
	private final ConsultManager consultService;
	private final AlertManager alertService;
	
	@Autowired
	public HomeController(ConsultManager consultService, AlertManager alertService) {
		this.consultService = consultService;
		this.alertService = alertService;
	}
	
	@RequestMapping(value = "/home", method=RequestMethod.GET) 
	public ModelAndView goHome(HttpServletRequest req, ModelAndView mav, RedirectAttributes rttr) { 
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		int role = (int) session.getAttribute("role");
		
		List<Consult> consultList = consultService.getUserSchedules(userId);
		if (consultList != null) {
			mav.setViewName("home");
			mav.addObject("schedules", consultList);
		}

		// newAlerts속성이 존재하지 않는 경우, 최초 로그인한 상태를 의미 - 상담 예정일 알림 생성
		if (session.getAttribute("newAlerts") == null) {
			boolean success = alertService.addAlertByConsultDate(userId, role, consultList);
			if (!success) {
				rttr.addFlashAttribute("addAlertFailed", true);
			}
		}
		
		// 알림 개수 갱신
		int newAlertCount = alertService.getNewAlertCount(userId);
		session.setAttribute("newAlerts", newAlertCount);
		
		return mav; 
	} 
}
