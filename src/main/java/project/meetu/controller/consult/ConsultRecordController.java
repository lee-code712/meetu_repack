package project.meetu.controller.consult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		
		if (consultId == null) {
			
		}
		Consult consult = consultService.getReservationInfo(consultId);
		model.addAttribute("consult", consult);
		
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
