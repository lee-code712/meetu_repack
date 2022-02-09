package project.meetu.controller.consult;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationController {
	
	@GetMapping("/consult/viewReservation")
	public String goReservationView(@RequestParam("consultId") String consultId, Model model) {
		return "reservation/reservationView";
	}

}
