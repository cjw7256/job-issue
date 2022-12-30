package sample.project.jobissue.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.PreRecruitment;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.repository.PreRecruitmentRepository;
import sample.project.jobissue.session.SessionManager;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/manageOpening")
public class ManageOpeningController {

	private final PreRecruitmentRepository preRecruitRepository;

	@GetMapping
//	public String manageOpening(Model model, @PathVariable("corCode") int corCode){
	public String manageOpening(Model model, HttpServletRequest req) {

		HttpSession session = req.getSession(false);
		
		if(session == null || session.getAttribute(SessionManager.SESSION_COOKIE_NAME) == null) {
			log.info("Session empty redirect to login Page");
			return "redirect:/user/login";
		}
		
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		int corCode = userVO.getCorCode();
		
		List<PreRecruitment> preRecruits = preRecruitRepository.selectByCorCode(corCode);
//
		model.addAttribute("preRecruits", preRecruits);
		log.info("preRecruits {}", preRecruits);

		return "/corporation/manageOpening";
	}

}