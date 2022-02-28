package project.meetu.controller.consult;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import project.meetu.model.dto.College;
import project.meetu.model.dto.Department;
import project.meetu.model.dto.Professor;
import project.meetu.model.service.AlertManager;
import project.meetu.model.service.UserManager;

@Controller
public class ProfessorSearchController {

	private final UserManager userService;
	private final AlertManager alertService;
	
	@Autowired
	public ProfessorSearchController(UserManager userService, AlertManager alertService) {
		this.userService = userService;
		this.alertService = alertService;
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
		
		// 알림 개수 갱신
		int newAlertCount = alertService.getNewAlertCount(userId);
		session.setAttribute("newAlerts", newAlertCount);
				
		return "consult/professorSearchPage";
	}
	
	@GetMapping(value = "/consult/professorSearchByDeptNo")
	public ModelAndView ProfessorSearch(HttpServletRequest req, ModelAndView mav) {
		
		String deptNo = (String) req.getParameter("deptNo");
		
		List<Professor> professorList = userService.getDeptProfessors(deptNo);
		if (professorList != null) {
			mav.addObject("professors", professorList);
		}
		
		List<College> collegeList = userService.getColleges();
		if (collegeList != null) {
			mav.addObject("colleges", collegeList);
		}
		
		List<Department> departmentList = userService.getDepartments();
		if (departmentList != null) {
			mav.addObject("departments", departmentList);
		}
		
		mav.setViewName("consult/professorSearchPage");
		return mav;
	}
}
