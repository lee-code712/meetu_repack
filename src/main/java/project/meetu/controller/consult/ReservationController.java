package project.meetu.controller.consult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.meetu.model.dto.Consult;
import project.meetu.model.service.ConsultManager;

@Controller
public class ReservationController {
	
	private final ConsultManager consultService;
	
	@Autowired
	public ReservationController(ConsultManager consultService) {
		this.consultService = consultService;
	}
	
	@GetMapping("/consult/viewReservation")
	public String goReservationView(@RequestParam("consultId") String consultId, Model model) {
		
		Consult reservation = consultService.getReservationInfo(consultId);
		model.addAttribute("reservation", reservation);
		
		return "consult/reservationView";
		
	}

}
