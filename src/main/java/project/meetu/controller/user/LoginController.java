package project.meetu.controller.user;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.meetu.authentication.MyAuthentication;
import project.meetu.model.dto.Member;
import project.meetu.model.dto.ServiceUser;
import project.meetu.model.service.AlertManager;
import project.meetu.model.service.UserManager;
import project.meetu.model.service.exception.LoginException;

@Controller
public class LoginController {
	
	private final UserManager userService;
	private final AlertManager alertService;
	
	@Autowired // 컨테이너에 있는 서비스와 연결됨
	public LoginController(UserManager userService, AlertManager alertService) {
		this.userService = userService;
		this.alertService = alertService;
	}

	@GetMapping("/")
	public String goLoginForm() {
		return "user/loginForm";
	}
	
	@PostMapping("/user/login") // POST방식으로 데이터 전달 시 사용
	public String login(@RequestParam("role") String role, ServiceUser user, 
			HttpServletRequest req, RedirectAttributes rttr) {
		
		user.setMemberInfo(new Member());
		user.getMemberInfo().setRole(Integer.parseInt(role));	
		
		try {
			user = userService.login(user);
			
			HttpSession session = req.getSession();
			session.setAttribute("id", user.getUserId());
			session.setAttribute("name", user.getMemberInfo().getName());
			session.setAttribute("role", user.getMemberInfo().getRole());
			
			return "redirect:/home";
			
		} catch (LoginException e) {
			e.printStackTrace(); // exception console에서 확인
			
			rttr.addFlashAttribute("loginFailed", true); // redirect시 값 전달
			rttr.addFlashAttribute("exception", e.getMessage());
			return "redirect:/";
		}
	
	}
	
	@GetMapping("/user/logout")
	public String logout(HttpServletRequest req, RedirectAttributes rttr) {
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		alertService.removeReadAlert(userId);
		
		session.invalidate();
		
		String changePwdSuccess = (String) req.getParameter("changePwdSuccess");
		if (changePwdSuccess != null) {
			rttr.addFlashAttribute("changePwdSuccess", true);
		}
		String resignSuccess = (String) req.getParameter("resignSuccess");
		if (resignSuccess != null) {
			rttr.addFlashAttribute("resignSuccess", true);
		}
		
	    return "redirect:/";
	}
	
	@GetMapping("/user/findPwdForm")
	public String findPwd() {
		return "user/findPwdForm";
	}
	
	@PostMapping("/user/findPwd") // POST방식으로 데이터 전달 시 사용
	public String findPwd(@RequestParam("userId") String userId, HttpServletRequest req, RedirectAttributes rttr) {
		ServiceUser serviceUser = userService.getUserByUserId(userId);
		
		if (serviceUser == null) {
			rttr.addFlashAttribute("findPwdFailed", true);
		}
		else {
			String email = serviceUser.getMemberInfo().getEmail();
			
			if (email != null && !email.equals("")) {
				String mail_title = "meetU 비밀번호 찾기";
				String sender = "swddwu@gmail.com";
				String sender_name = "meetU 관리자";
				
				Session sess = null;
				Message msg = null;
				Properties props = null;
				
				try {
					props = new Properties();
					props.put("mail.transport.protocol", "smtp");
					props.put("mail.smtp.host", "smtp.gmail.com");
					props.put("mail.smtp.port", "587");
				    props.put("mail.smtp.ssl.enable", "true");
				    props.put("mail.smtp.auth", "true");
				    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
				    props.put("mail.smtp.starttls.enable", "true");
				    props.put("mail.smtp.socketfactory.class", "javax.net.ssl.SSLSocketFactory");
				    props.put("mail.smtp.starttls.required", "true");
				    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
				    
				    Authenticator auth = new MyAuthentication(sender, "ddwusw19");
					sess = Session.getDefaultInstance(props, auth);
					
					msg = new MimeMessage(sess);
					msg.setFrom(new InternetAddress(sender, sender_name, "UTF-8"));
					InternetAddress[] address = {new InternetAddress(email)};
					msg.setRecipients(Message.RecipientType.TO, address);
					msg.setSubject(mail_title);
					
					
					String mail_body = "meetU 비밀번호 찾기 메일입니다. 회원님의 비밀번호는 " + serviceUser.getPassword() + " 입니다.";
					
					msg.setContent(mail_body, "text/html;charset=UTF-8");
					msg.setHeader("Content-Transfer-Encoding", "base64");
					msg.setSentDate(new java.util.Date());
					Transport.send(msg);
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
			rttr.addFlashAttribute("findPwdSuccess", true);
		}

		return "redirect:/";	
	}
	
	@GetMapping("/user/findIdForm")
	public String findId() {
		return "user/findIdForm";
	}
	
	@PostMapping("/user/findId") // POST방식으로 데이터 전달 시 사용
	public String findId(@RequestParam("memberNo") String memberNo, HttpServletRequest req, RedirectAttributes rttr) {
		ServiceUser serviceUser = userService.getUserByMemberNo(memberNo);
		
		if (serviceUser == null) {
			rttr.addFlashAttribute("findIdFailed", true);
		}
		else {
			rttr.addFlashAttribute("userId", serviceUser.getUserId());
		}

		return "redirect:/";	
	}
}
