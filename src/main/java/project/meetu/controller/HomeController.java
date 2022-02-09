package project.meetu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import project.meetu.model.dto.Consult;
import project.meetu.model.service.ConsultManager;

@Controller 
public class HomeController { 
	
	private final ConsultManager consultService;
	
	@Autowired
	public HomeController(ConsultManager consultService) {
		this.consultService = consultService;
	}
	
	@RequestMapping(value = "/home", method=RequestMethod.GET) 
	public ModelAndView goHome(HttpServletRequest req, ModelAndView mav) { 
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		
		List<Consult> consultList = consultService.getUserSchedules(userId);
		if (consultList != null) {
			mav.setViewName("content/home");
			mav.addObject("schedules", consultList);
		}
		
		return mav; 
	} 
}
