package project.meetu.controller;

import java.util.List;

import org.apache.catalina.startup.CertificateCreateRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import project.meetu.model.dto.Member;
import project.meetu.model.service.MemberService;

@Controller
public class MemberController {
	
	private final MemberService memberService; // 공통기능을 담당하는 서비스는 컨테이너에 등록해서 컨트롤러끼리 공유해서 사용

	@Autowired // 컨테이너에 있는 서비스와 연결됨
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("members/new")
	public String createForm() {
		return "members/createMemberForm";
	}
	
	@PostMapping("members/new") // POST방식으로 데이터 전달 시 사용 - 보통 데이터 등록 시 사용하는 방식
	public String create(Member member) {
		// System.out.println("member = " + member.getName());
		
		memberService.join(member);	
		return "redirect:/";
	}
	
	@GetMapping("/members")
	public String list(Model model) {
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "members/memberList";
	}
}
