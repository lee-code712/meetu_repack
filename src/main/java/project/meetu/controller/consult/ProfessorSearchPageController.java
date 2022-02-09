package project.meetu.controller.consult;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import project.meetu.model.dto.College;
import project.meetu.model.dto.Consult;
import project.meetu.model.dto.Course;
import project.meetu.model.dto.Department;
import project.meetu.model.dto.Professor;
import project.meetu.model.service.ConsultManager;
import project.meetu.model.service.UserManager;

@Controller
public class ProfessorSearchPageController {

	private final UserManager userService;
	
	@Autowired
	public ProfessorSearchPageController(UserManager userService) {
		this.userService = userService;
	}
	
	@GetMapping(value = "/consult/professorSearch")
	public String goProfessorSearchPage(HttpServletRequest req, Model model) {
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		
		List<College> collegeList = userService.getColleges();
		if (collegeList != null) {
			model.addAttribute("colleges", collegeList);
		}
		
		List<Department> departmentList = userService.getDepartments();
		if (departmentList != null) {
			model.addAttribute("departments", departmentList);
		}
		
		String keyword = (String) req.getParameter("keyword");
		if (keyword != null) {
			List<Professor> professorList = userService.getProfessorsByKeyword(keyword);
			if (professorList != null) {
				model.addAttribute("professors", professorList);
			}
		}
		
		return "consult/professorSearchPage";
	}
	
	@PostMapping(value = "/consult/professorSearch")
	@ResponseBody
	public List<Professor> ProfessorSearch(HttpServletRequest req) {
		
		String deptNo = (String) req.getParameter("deptNo");
		
		List<Professor> professorList = userService.getDeptProfessors(deptNo);
		
		return professorList;
	}
}
