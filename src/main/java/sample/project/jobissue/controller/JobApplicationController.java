package sample.project.jobissue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
import sample.project.jobissue.repository.JobApplicationRepository;
import sample.project.jobissue.session.SessionManager;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/submit")
public class JobApplicationController {
	
	private final JobApplicationRepository jobApplicationRepository;
	
	@PostMapping("/submit")
	public String list2(Model model, @RequestParam int resumeUserCode,
			 HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		ResumeItem resumeItem = new ResumeItem();
		resumeItem.setUserCode(userVO.getUserCode());
		resumeItem = jobApplicationRepository.selectByUserResume(resumeUserCode);
		model.addAttribute("submitResume", resumeItem);
		log.info("레주메 {}",resumeItem);
		return "/submit/submitResume";
	}
	
	@GetMapping("/{resumeUserCode}")
	public String list(Model model, @PathVariable("resumeUserCode") int resumeUserCode,
			 HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		ResumeItem resumeItem = new ResumeItem();
		resumeItem.setUserCode(userVO.getUserCode());
		resumeItem = jobApplicationRepository.selectByUserResume(resumeUserCode);
		model.addAttribute("submitResume", resumeItem);
		log.info("레주메 {}",resumeItem);
		return "/submit/submitResume";
	}
}
