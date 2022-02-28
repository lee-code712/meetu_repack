package project.meetu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.meetu.model.dto.Alert;
import project.meetu.model.service.AlertManager;

@Controller 
public class AlertController {

	private final AlertManager alertService;
	
	@Autowired
	public AlertController(AlertManager alertService) {
		this.alertService = alertService;
	}
	
	@RequestMapping("/alert")
	public String goAlertPage(HttpServletRequest req, Model model, RedirectAttributes rttr) {
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		
		List<Alert> newAlerts = alertService.getNewAlerts(userId);
		if (newAlerts.size() > 0) {
			model.addAttribute("alerts", newAlerts);
		}
		else {
			model.addAttribute("notFound", true);
		}
		
		boolean success = alertService.changeReadState(userId);
		if (!success) {
			rttr.addFlashAttribute("changeFailed", true);
		}
		
		return "alert";
	}
}
