package project.meetu.controller.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.core.joran.conditional.IfAction;
import project.meetu.model.dto.College;
import project.meetu.model.dto.Consult;
import project.meetu.model.dto.Department;
import project.meetu.model.dto.Member;
import project.meetu.model.dto.Message;
import project.meetu.model.dto.Professor;
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
	public String goMessagePage(HttpServletRequest req, RedirectAttributes rttr, Model model) {
		HttpSession session = req.getSession();
		
		String id = (String) session.getAttribute("id");
		int role = (Integer) session.getAttribute("role");
		
		ArrayList<Consult> consults = (ArrayList<Consult>) consultService.getUserConsults(id);
		
		if(consults != null) {
			HashMap<String, ArrayList<String>> memberMap = new HashMap<String, ArrayList<String>>(); 
			Iterator<Consult> iterator = consults.iterator();
			
			while(iterator.hasNext()) {
				Consult consult = iterator.next();
				if(consult.getStatus() == 1) { // 예약이 승인된 경우에만
					String userId;
					
					if(role == 0) {
						userId = consult.getStuUser().getUserId();
					}
					else {
						userId = consult.getProfUser().getUserId();;
					}
					
					ServiceUser su = userService.getUserByUserId(userId);
					Department department = su.getMemberInfo().getDeptInfo();
					ArrayList<String> memberList = new ArrayList<>();
					memberList.add(department.getDeptName());
					memberList.add(su.getMemberInfo().getName());
					Integer consultId = consult.getId();
					memberList.add(consultId.toString());
					memberList.add(userId);
					
					memberMap.put(userId, memberList); // key-상대방의 id, value-상대방의 학과 및 이름 및 예약 id
				}
			}
			
			model.addAttribute("memberMessage", memberMap);
			return "message/messageView";
		}
		else {
			rttr.addFlashAttribute("emptyReservation", true);
			return "redirect:/home";
		}
	}
	
	@GetMapping(value = "/message/searchMessages")
	public ModelAndView ProfessorSearch(HttpServletRequest req, ModelAndView mav) {
		HttpSession session = req.getSession();
		
		String id = (String) session.getAttribute("id");
		int role = (Integer) session.getAttribute("role");
		String memMsgId = (String) req.getParameter("memMsgId");
		String memMsgName = (String) req.getParameter("memMsgName");
		String consultId = (String) req.getParameter("consultId");
		
		messageService.changeRead(id, memMsgId);
		ArrayList<Message> messages = messageService.getMessages(id, memMsgId);
		
		if (messages != null) {
			mav.addObject("messages", messages);
		}
		
		ArrayList<Consult> consults = (ArrayList<Consult>) consultService.getUserConsults(id);
		
		if(consults != null) {
			HashMap<String, ArrayList<String>> memberMap = new HashMap<String, ArrayList<String>>(); 
			Iterator<Consult> iterator = consults.iterator();
			
			while(iterator.hasNext()) {
				Consult consult = iterator.next();
				if(consult.getStatus() == 1) { // 예약이 승인된 경우에만
					String userId;
					
					if(role == 0) {
						userId = consult.getStuUser().getUserId();
					}
					else {
						userId = consult.getProfUser().getUserId();;
					}
					
					ServiceUser su = userService.getUserByUserId(userId);
					Department department = su.getMemberInfo().getDeptInfo();
					ArrayList<String> memberList = new ArrayList<>();
					memberList.add(department.getDeptName());
					memberList.add(su.getMemberInfo().getName());
					Integer consultIdInt = consult.getId();
					memberList.add(consultIdInt.toString());
					memberList.add(userId);
					
					memberMap.put(userId, memberList); // key-상대방의 id, value-상대방의 학과 및 이름 및 예약 id
				}
			}
			mav.addObject("memberMessage", memberMap);
		}
		
		mav.addObject("memMsgId", memMsgId);
		mav.addObject("memMsgName", memMsgName);
		mav.addObject("consultId", consultId);
		mav.setViewName("message/messageView");
		return mav;
	}
	
	@PostMapping(value = "/message/sendMessage")
	public ModelAndView SendMessage(HttpServletRequest req, ModelAndView mav) {
		HttpSession session = req.getSession();
		
		String id = (String) session.getAttribute("id");
		int role = (Integer) session.getAttribute("role");
		String memMsgId = (String) req.getParameter("memMsgId");
		String memMsgName = (String) req.getParameter("memMsgName");
		String content = (String) req.getParameter("content");

		Message message = new Message();
		message.setContent(content);
		message.setRecvId(memMsgId);
		message.setSendId(id);
		
		messageService.addMessage(message);
		
		ArrayList<Message> messages = messageService.getMessages(id, memMsgId);
		
		if (messages != null) {
			mav.addObject("messages", messages);
		}
		
		ArrayList<Consult> consults = (ArrayList<Consult>) consultService.getUserConsults(id);
		
		if(consults != null) {
			HashMap<String, ArrayList<String>> memberMap = new HashMap<String, ArrayList<String>>(); 
			Iterator<Consult> iterator = consults.iterator();
			
			while(iterator.hasNext()) {
				Consult consult = iterator.next();
				if(consult.getStatus() == 1) { // 예약이 승인된 경우에만
					String userId;
					
					if(role == 0) {
						userId = consult.getStuUser().getUserId();
					}
					else {
						userId = consult.getProfUser().getUserId();;
					}
					
					ServiceUser su = userService.getUserByUserId(userId);
					Department department = su.getMemberInfo().getDeptInfo();
					ArrayList<String> memberList = new ArrayList<>();
					memberList.add(department.getDeptName());
					memberList.add(su.getMemberInfo().getName());
					Integer consultId = consult.getId();
					memberList.add(consultId.toString());
					memberList.add(userId);
					
					memberMap.put(userId, memberList); // key-상대방의 id, value-상대방의 학과 및 이름 및 예약 id
				}
			}
			mav.addObject("memberMessage", memberMap);
			
			// 알림 추가
			ServiceUser su = userService.getUserByUserId(id);
			alertService.addAlertByaddMessage(su.getMemberInfo().getName(), memMsgId);
		}
		mav.addObject("memMsgId", memMsgId);
		mav.addObject("memMsgName", memMsgName);
		mav.setViewName("message/messageView");
		return mav;
	}
}
