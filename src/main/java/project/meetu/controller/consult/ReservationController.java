package project.meetu.controller.consult;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.meetu.model.dto.Consult;
import project.meetu.model.dto.Professor;
import project.meetu.model.dto.ServiceUser;
import project.meetu.model.service.ConsultManager;
import project.meetu.model.service.UserManager;

@Controller
public class ReservationController {
	
	private final ConsultManager consultService;
	private final UserManager userService;
	
	@Autowired
	public ReservationController(ConsultManager consultService, UserManager userService) {
		this.consultService = consultService;
		this.userService = userService;
	}
	
	@GetMapping("/consult/viewReservation")
	public String goReservationView(@RequestParam("consultId") String consultId, Model model) {
		
		Consult reservation = consultService.getReservationInfo(consultId);
		model.addAttribute("reservation", reservation);
		
		return "consult/reservationView";
		
	}
	
	@GetMapping("/consult/changeStatus")
	public String changeStatus(Consult reservation, RedirectAttributes rttr) {
		
		boolean success = consultService.changeReservationStatus(reservation);
		if (!success) {
			rttr.addFlashAttribute("changeFailed", true);
		}
		
		return "redirect:/user/my";
		
	}
	
	@GetMapping("/consult/reservationForm")
	public String goReservationForm(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		String profNo = (String) req.getParameter("profNo");
		
		// 교수의 회원 ID 구함
		ServiceUser profUser = userService.getUserByMemberNo(profNo);
		String profId = profUser.getUserId();
		
		// 예약 페이지에서 보일 교수 정보 반환
		Professor professorInfo = userService.getProfessorByMemberNo(profNo);
		if (professorInfo != null) {
			model.addAttribute("professorInfo", professorInfo);
		}
		
		// 같은 교수에게 예약 레코드가 있는지 여부 구함	
		boolean isReservated = consultService.checkReservated(userId, profId);
		if(isReservated) { 
			return "consult/professorSearchPage?isReservated=1"; // 예약 레코드가 있는 경우 교수 선택 페이지로 리턴
		}
		
		return "consult/reservationForm";
	}

}
