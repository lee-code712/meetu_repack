package project.meetu.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.meetu.authentication.MyAuthentication;
import project.meetu.model.dto.Member;
import project.meetu.model.dto.ServiceUser;
import project.meetu.model.service.UserManager;

@Controller
public class JoinController {
	
	private final UserManager userService;
	
	@Autowired // 컨테이너에 있는 서비스와 연결됨
	public JoinController(UserManager userService) {
		this.userService = userService;
	}

	@GetMapping("/user/joinForm")
	public String join() {
		return "user/join";
	}

	@PostMapping("/user/join") // POST방식으로 데이터 전달 시 사용
	public String join(ServiceUser serviceUser, HttpServletRequest req, RedirectAttributes rttr, Model model) {
		HttpSession session = req.getSession();
		
		// 유효한 회원가입 요청인지 확인
		Member member = userService.registerCheck(serviceUser);
		
		if (member == null) {
			rttr.addFlashAttribute("registerFailed", true);
			return "redirect:/";
		}
		
		// 유효한 요청이라면, 확인 메일 전송
		serviceUser.setMemberInfo(member);
		String email = member.getEmail();
		
		if (email != null && !email.equals("")) {
			String mail_title = "meetU 회원가입 이메일 인증";
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
				
				Random rand = new Random();
				String n = Integer.toString(rand.nextInt(10000));
				
				String mail_body = "meetU 회원가입 인증 메일입니다. 인증 란에 [ " + n + " ]를 입력해 주세요.";
				
				session.setAttribute("authNum", n);
				
				msg.setContent(mail_body, "text/html;charset=UTF-8");
				msg.setHeader("Content-Transfer-Encoding", "base64");
				msg.setSentDate(new java.util.Date());
				Transport.send(msg);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		session.setAttribute("serviceUser", serviceUser);
		return "user/joinAuth";
	}
	
	@GetMapping("/user/joinAuthForm")
	public String joinAuth() {
		return "user/joinAuth";
	}
	
	@PostMapping("/user/joinAuth") 
	public String joinAuth(String inputCode, HttpServletRequest req, RedirectAttributes rttr) {
		HttpSession session = req.getSession();
		ServiceUser serviceUser = (ServiceUser) session.getAttribute("serviceUser");
		String authNum = (String) session.getAttribute("authNum");
		
		if (authNum.equals(inputCode)) {
			boolean success = userService.register(serviceUser);
			
			if (!success) {
				rttr.addFlashAttribute("registerFailed", true);
				return "redirect:/";
			}
		}
		else {
			rttr.addFlashAttribute("authFailed", true);
			return "redirect:/";
		}

		session.removeAttribute("authNum");
		session.removeAttribute("serviceUser");
		rttr.addFlashAttribute("registerSucces", true);
		return "redirect:/";
	}
}
