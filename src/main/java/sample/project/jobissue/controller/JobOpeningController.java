package sample.project.jobissue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/jobOpening")
public class JobOpeningController {

	@GetMapping
	public String jobOpening(Model model, HttpServletRequest req){
		
		
		
		return "jobOpening";
		
	}
}
