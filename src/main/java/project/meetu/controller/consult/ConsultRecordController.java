package project.meetu.controller.consult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.meetu.model.dto.Consult;
import project.meetu.model.service.ConsultManager;

@Controller
public class ConsultRecordController {
	
	private final ConsultManager consultService;
	
	@Autowired
	public ConsultRecordController(ConsultManager consultService) {
		this.consultService = consultService;
	}

	@GetMapping("/consult/recordConsult")
	public String goConsultRecordForm(String consultId, Model model) {
		
		Consult consult = consultService.getReservationInfo(consultId);
		model.addAttribute("consult", consult);
		
		return "consult/consultRecordForm";
	}
	
}
