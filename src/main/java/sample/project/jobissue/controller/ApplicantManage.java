package sample.project.jobissue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.repository.JobRepository;
import sample.project.jobissue.session.SessionManager;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/applicantManage")
public class ApplicantManage {

	private final JobRepository jobRepository;
	
	@GetMapping
	public String manageOpening(Model model, HttpServletRequest req){
		
//		HttpSession session = req.getSession(false);
//		
//		if(session == null || session.getAttribute(SessionManager.SESSION_COOKIE_NAME) == null) {
//			log.info("Session empty redirect to login Page");
//			return "redirect:/user/login";
//		}
//		
//		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
//		int corCode = userVO.getCorCode();
//		
//		List<JobItem> jobItem = jobRepository
		
		return "corporation/applicantManage";
	}
}
