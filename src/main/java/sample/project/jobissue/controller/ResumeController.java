package sample.project.jobissue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import sample.project.jobissue.domain.ResumeItem;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.repository.ResumeRepository;
import sample.project.jobissue.session.SessionManager;
import sample.project.jobissue.validation.ResumeValidator;


@Slf4j
//@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/resumes")
public class ResumeController {

	private final ResumeRepository resumeRepository;
	
	private final ResumeValidator resumeValidator;
	
	
	@GetMapping("/resumes")
	public String resumes(Model model,
			 HttpServletRequest req) {
		
		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		ResumeItem resumeItem = new ResumeItem();
		resumeItem.setUserCode(userVO.getUserCode());
		resumeItem = resumeRepository.selectByUserCode(resumeItem.getUserCode());
		model.addAttribute("resumes", resumeItem);
		log.info("레주메 {}",resumeItem);
		return "resumes/resumes";
	}
	
	@PostMapping("/resume")
	public String list2(Model model, @RequestParam int resumeUserCode) {
		ResumeItem resumeItem = resumeRepository.selectByUserCode(resumeUserCode);
		model.addAttribute("resume", resumeItem);
		log.info("레주메 {}",resumeItem);
		return "/resumes/resume";
	}
	
	@GetMapping("/{resumeUserCode}")
	public String list(Model model, @PathVariable("resumeUserCode") int resumeUserCode) {
		ResumeItem resumeItem = resumeRepository.selectByUserCode(resumeUserCode);
		model.addAttribute("resume", resumeItem);
		log.info("레주메 {}",resumeItem);
		return "/resumes/resume";
	}
	
	@GetMapping("/insert")
	public String newWrite(Model model) {
		ResumeItem resumeItem = new ResumeItem();
		model.addAttribute("resumeItem", resumeItem);
		
		return "resumes/insert";
	}
	
	@PostMapping("/insert")
	public String newMemberInsert(@ModelAttribute ResumeItem resumeItem
			, BindingResult bindingResult
			, HttpServletRequest req) {
		
		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		resumeItem.setUserCode(userVO.getUserCode());
		
		resumeValidator.validate(resumeItem, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "resumes/insert";
		}
		
		log.info("resumeItem = {}", userVO);
		log.info("resumeItem = {}", resumeItem);
		resumeRepository.insertResume(resumeItem);
		return "redirect:/resumes/resumes";
	}
	
	@GetMapping("/update/{userCode}")
	public String updateResume(Model model, @PathVariable("userCode") int userCode
			, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		ResumeItem resumeItem = new ResumeItem();
		
		resumeItem.setUserCode(userVO.getUserCode());
		resumeItem = resumeRepository.selectByUserCode(userCode);
		model.addAttribute("resume", resumeItem);
		
		return "resumes/update";
	}
	
	@PostMapping("/update/{userCode}")
	public String updateResumeProcess(Model model
			, @PathVariable("userCode") int userCode
			, @ModelAttribute ResumeItem resumeItem ) {
		log.info(resumeItem.toString());
		log.info("/update/{}", resumeItem);
		
		resumeRepository.update(userCode, resumeItem);

		
		return "redirect:/resumes/{userCode}";
	}
	
	
	@GetMapping("/delete/{userCode}")
	public String deleteResume(Model model, @PathVariable("userCode") int userCode
			, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		ResumeItem resumeItem = new ResumeItem();
		resumeItem.setUserCode(userVO.getUserCode());
		resumeRepository.deleteResume(userCode, resumeItem);
		return "resumes/resumes";
	}
	
	
	
}
