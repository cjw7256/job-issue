package sample.project.jobissue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/applicantManage")
public class ApplicantManage {

	@GetMapping
	public String manageOpening(Model model, HttpServletRequest req){
		return "corporation/applicantManage";
	}
}
