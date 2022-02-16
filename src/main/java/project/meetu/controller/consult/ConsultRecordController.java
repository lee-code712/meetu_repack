package project.meetu.controller.consult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsultRecordController {

	@GetMapping("/consult/recordConsult")
	public String goConsultRecordForm() {

		
		return "consult/consultRecordForm";
	}
	
}
