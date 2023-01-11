package sample.project.jobissue.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.ApplicantInfo;
import sample.project.jobissue.domain.ApplicantManage;
import sample.project.jobissue.domain.PageMaker;
import sample.project.jobissue.domain.PreRecruitment;
import sample.project.jobissue.domain.SearchItem;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.repository.PreRecruitmentRepository;
import sample.project.jobissue.session.SessionManager;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/applicantManage")
public class ApplicantManageController {

	private final PreRecruitmentRepository preRecruitment;
	
	//검색창을 띄우기 위한 model 추가
		@ModelAttribute("pageMaker")
		public PageMaker searchItem(SearchItem si) {
			if (si == null) {
				si = new SearchItem();
			}
			PageMaker pageMaker = new PageMaker(si);
			
			return pageMaker;
		}
	
	@GetMapping
	public String manageOpening(Model model, HttpServletRequest req){
		
		HttpSession session = req.getSession(false);
		
		if(session == null || session.getAttribute(SessionManager.SESSION_COOKIE_NAME) == null) {
			log.info("Session empty redirect to login Page");
			return "redirect:/user/login";
		}
		
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		int corCode = userVO.getCorCode();
//		int announcementCode =  
		
		List<ApplicantManage> applicants = preRecruitment.selectByCorCode(corCode);
//		ApplicantManage submitResume = preRecruitment.selectByAnnSubmit();
		for(ApplicantManage am : applicants) {
			 List<ApplicantInfo> applicantInfos = preRecruitment.selectByAnnSubmit(am.getAnnouncementCode());
			 am.setApplicantInfos(applicantInfos);
		}
		
		model.addAttribute("Recruit",applicants);
		//지원자목록
		
		
//		model.addAttribute("SubmitResume", submitResume);
		
		return "corporation/applicantManage";
	}
	
	
	
	@GetMapping("/submitResume/{userCode}/{announcementCode}")
	public String submitResume(Model model, @PathVariable("userCode") int userCode
			, @PathVariable("announcementCode") int announcementCode) {
		
		ApplicantInfo submitResume = preRecruitment.userSelectByAnnCode(announcementCode);
		log.info("announcementCode {}", announcementCode);
	
		log.info("submit resume {}", submitResume);
		model.addAttribute("submitResume", submitResume);
		
		
		return "corporation/submitResume";
	}
}
