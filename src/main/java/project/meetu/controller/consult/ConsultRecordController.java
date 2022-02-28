package project.meetu.controller.consult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.meetu.model.dto.Consult;
import project.meetu.model.service.AlertManager;
import project.meetu.model.service.ConsultManager;

@Controller
public class ConsultRecordController {
	
	private final ConsultManager consultService;
	private final AlertManager alertService;
	
	@Autowired
	public ConsultRecordController(ConsultManager consultService, AlertManager alertService) {
		this.consultService = consultService;
		this.alertService = alertService;
	}

	@GetMapping("/consult/recordConsult")
	public String goConsultRecordForm(String consultId, Model model, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");

		Consult consult = consultService.getReservationInfo(consultId);
		model.addAttribute("consult", consult);
		
		// 알림 개수 갱신
		int newAlertCount = alertService.getNewAlertCount(userId);
		session.setAttribute("newAlerts", newAlertCount);
		
		return "consult/consultRecordForm";
	}
	
	@PostMapping("/consult/changeRecord")
	public String changeRecord(Consult consult, RedirectAttributes rttr) {
		
		boolean success = consultService.changeConsultContent(consult);
		if (!success) {
			rttr.addFlashAttribute("changeFailed", true);
		}
		
		return "redirect:/consult/recordConsult?consultId=" + consult.getId();
	
	}
	
}
