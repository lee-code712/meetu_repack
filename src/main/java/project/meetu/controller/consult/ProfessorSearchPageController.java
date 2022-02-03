package project.meetu.controller.consult;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class ProfessorSearchPageController {

	private final ConsultManager consultService;
	
	@Autowired
	public ProfessorSearchPageController(ConsultManager consultService) {
		this.consultService = consultService;
	}
	
	@GetMapping(value = "/consult/professorSearch")
	public String goProfessorSearchPage(HttpServletRequest req, Model model) {
		
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("id");
		
		List<College> collegeList = consultService.getColleges();
		if (collegeList != null) {
			model.addAttribute("colleges", collegeList);
		}
		
		List<Department> departmentList = consultService.getDepartments();
		if (departmentList != null) {
			model.addAttribute("departments", departmentList);
		}
		
		return "consult/professorSearchPage";
	}
	
	@PostMapping(value = "/consult/professorSearch")
	@ResponseBody
	public ModelAndView ProfessorSearch(ModelAndView mav, HttpServletRequest req) {
		
		ModelAndView mView = new ModelAndView("jsonView"); 
		
		String deptNo = (String) req.getParameter("deptNo");

		List<Professor> professorList = consultService.getDeptProfessors(deptNo);
		if (professorList != null) {
			mView.addObject("professors", professorList);
		}
		
		return mView;
	}
}
