package project.meetu.controller.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.meetu.model.dto.Consult;
import project.meetu.model.dto.Department;
import project.meetu.model.dto.Member;
import project.meetu.model.dto.ServiceUser;
import project.meetu.model.service.AlertManager;
import project.meetu.model.service.ConsultManager;
import project.meetu.model.service.MessageManager;
import project.meetu.model.service.UserManager;

@Controller
public class MessageController {
	private final MessageManager messageService;
	private final UserManager userService;
	private final AlertManager alertService;
	private final ConsultManager consultService;
	
	@Autowired // 컨테이너에 있는 서비스와 연결됨
	public MessageController(MessageManager messageService, UserManager userService, AlertManager alertService, ConsultManager consultService) {
		this.messageService = messageService;
		this.userService = userService;
		this.alertService = alertService;
		this.consultService = consultService;
	}
	
	@GetMapping("/message")
	public String goMessagePage(HttpServletRequest req, RedirectAttributes rttr) {
		HttpSession session = req.getSession();
		
		String id = (String) session.getAttribute("id");
		int role = (Integer) session.getAttribute("role");
		
		ServiceUser serviceUser = userService.getUserByUserId(id);
		
		ArrayList<Consult> consults = (ArrayList<Consult>) consultService.getUserConsults(id);
		
		if(consults != null) {
			HashMap<String, ArrayList<String>> memberMap = new HashMap<String, ArrayList<String>>(); 
			Iterator<Consult> iterator = consults.iterator();
			
			while(iterator.hasNext()) {
				Consult consult = iterator.next();
				if(consult.getStatus() == 1) { // 예약이 승인된 경우에만
					String userId;
					
					if(role == 0) {
						userId = consult.getProfUser().getUserId();
					}
					else {
						userId = consult.getStuUser().getUserId();;
					}
					
					Member member = serviceUser.getMemberInfo();
					Department department = member.getDeptInfo();
					ArrayList<String> memberList = new ArrayList<>();
					memberList.add(department.getDeptName());
					memberList.add(member.getName());
					Integer consultId = consult.getId();
					memberList.add(consultId.toString());
					
					memberMap.put(userId, memberList); // key-상대방의 id, value-상대방의 학과 및 이름 및 예약 id
				}
			}
			
			req.setAttribute("memberMessage", memberMap);
			return "message/messageView";
		}
		else {
			rttr.addFlashAttribute("emptyReservation", true);
			return "redirect:/home";
		}
	}
}
