package sample.project.jobissue.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


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
import sample.project.jobissue.domain.AcademicRecordCode;
import sample.project.jobissue.domain.CareerCode;
import sample.project.jobissue.domain.EmployTypeCode;
import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.MaritalStatus;
import sample.project.jobissue.domain.MilitaryStatus;
import sample.project.jobissue.domain.ReadingCode;
import sample.project.jobissue.domain.RecruitFieldCode;
import sample.project.jobissue.domain.ResumeItem;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.repository.JobApplicationRepository;
import sample.project.jobissue.repository.ResumeRepository;
import sample.project.jobissue.service.UserService;
import sample.project.jobissue.session.SessionManager;
import sample.project.jobissue.validation.ResumeValidator;


@Slf4j
//@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/resumes")
public class ResumeController {

	private final ResumeRepository resumeRepository;
	
	private final UserService userService;
	
	private final ResumeValidator resumeValidator;
	
	private final JobApplicationRepository jobApplicationRepository;
	
	@GetMapping("/submitLists")
	public String submitResumeLists(Model model, 
			HttpServletRequest req) {
		
	HttpSession session = req.getSession(false);
	UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
	List<JobItem> jobList = resumeRepository.selectBySubmit(userVO.getUserCode());
	model.addAttribute("submitLists",jobList);
	return "/resumes/submitLists";
	}

	@GetMapping("/submitLists/{submitListAnnouncementCode}")
	public String submitResumeListDetail(Model model, 
			@PathVariable("submitListAnnouncementCode") int submitListAnnCode, 
			@ModelAttribute JobItem jobItem, 
			HttpServletRequest req) {
		jobItem = resumeRepository.selectByAnnCode(submitListAnnCode);
		model.addAttribute("submitList", jobItem);
		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		ResumeItem resumeItem = jobApplicationRepository.selectByUserResume(userVO.getUserCode());
		model.addAttribute("submitResume2", resumeItem);
		session.setAttribute("jobjob", jobItem);
		return "/resumes/submitList";
	}
	
	@GetMapping("/resumes")
	public String resumesList(Model model,
			 HttpServletRequest req) {
		
		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		ResumeItem resumeItem = new ResumeItem();
		resumeItem.setUserCode(userVO.getUserCode());
		resumeItem = resumeRepository.selectByUserCode(resumeItem.getUserCode());
		model.addAttribute("resumes", resumeItem);
		return "resumes/resumes";
	}
	
	@GetMapping("/resumes/{resumeUserCode}")
	public String resumeDetail(Model model,
			@PathVariable("resumeUserCode") int resumeUserCode) {

		ResumeItem resumeItem = resumeRepository.selectByUserCode(resumeUserCode);
		model.addAttribute("resume", resumeItem);
		return "/resumes/resume";
	}
	
	
	@GetMapping("/insert")
	public String newWrite(Model model) {
		
		ResumeItem resumeItem = new ResumeItem();
		model.addAttribute("resumeItem", resumeItem);
		return "resumes/insert";
	}
	
	@PostMapping("/insert")
	public String newWritePrecess(@ModelAttribute ResumeItem resumeItem
			, BindingResult bindingResult
			, HttpServletRequest req
			) throws Exception {
		
		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		UserVO userVOInDB = userService.findUserByEmail(userVO.getUserEmail());
		resumeItem.setUserCode(userVO.getUserCode());
		resumeValidator.validate(resumeItem, bindingResult);
		if (bindingResult.hasErrors()) {
		log.info("error 발생");

			return "resumes/insert";
		}
		if (userVOInDB.getResumeCode().equals("N")) {
			resumeRepository.insertResume(resumeItem);
			resumeRepository.insertAfter(userVO.getUserCode(), userVO);
			return "redirect:/resumes/resumes";
		}
		return "redirect:/resumes/resumes";
	}
	
	@PostMapping("/resume/delete/{userCode}")
	public String deleteResume(Model model, 
			HttpServletRequest req) {

		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		ResumeItem resumeItem = new ResumeItem();
		resumeItem.setUserCode(userVO.getUserCode());
		resumeRepository.deleteResume(resumeItem.getUserCode(), resumeItem);
		resumeRepository.deleteAfter(userVO.getUserCode(), userVO);
		return "redirect:/resumes/resumes";
	}
	
	@PostMapping("/submitResume/delete/{userCode}")
	public String deleteSubmitResume(Model model, 
			HttpServletRequest req) {
		
		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		ResumeItem resumeItem = new ResumeItem();
		resumeItem.setUserCode(userVO.getUserCode());
		JobItem jobItem = (JobItem) session.getAttribute("jobjob");
		resumeRepository.deleteSubmitResume(resumeItem.getUserCode(), jobItem.getAnnouncementCode());
		return "redirect:/resumes/submitLists";
	}	
	
	
	@GetMapping("/update/{userCode}")
	public String updateResume(Model model, 
			@PathVariable("userCode") int userCode, 
			HttpServletRequest req) {
		
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

		resumeRepository.update(userCode, resumeItem);
		return "redirect:/resumes/update/{userCode}";
	}
	
	

	@ModelAttribute("employTypeCodes")
    public List<EmployTypeCode> employTypeCodes() {
        List<EmployTypeCode> employTypeCodes = new ArrayList<>();
        employTypeCodes.add(new EmployTypeCode("01", "정규직"));
        employTypeCodes.add(new EmployTypeCode("02", "계약직"));
        employTypeCodes.add(new EmployTypeCode("03", "인턴직"));
        employTypeCodes.add(new EmployTypeCode("04", "프리랜서"));
        employTypeCodes.add(new EmployTypeCode("05", "기타/아르바이트"));
        return employTypeCodes;
    }
	
	@ModelAttribute("recruitFieldCodes")
    public List<RecruitFieldCode> recruitFieldCodes() {
        List<RecruitFieldCode> recruitFieldCodes = new ArrayList<>();
        recruitFieldCodes.add(new RecruitFieldCode("01", "경영ㆍ사무ㆍ금융ㆍ보험직"));
        recruitFieldCodes.add(new RecruitFieldCode("02", "연구직 및 공학 기술직"));
        recruitFieldCodes.add(new RecruitFieldCode("03", "교육ㆍ법률ㆍ사회복지ㆍ경찰ㆍ소방직 및 군인"));
        recruitFieldCodes.add(new RecruitFieldCode("04", "보건ㆍ의료직"));
        recruitFieldCodes.add(new RecruitFieldCode("05", "예술ㆍ디자인ㆍ방송ㆍ스포츠직"));
        recruitFieldCodes.add(new RecruitFieldCode("06", "미용ㆍ여행ㆍ숙박ㆍ음식ㆍ경비ㆍ청소직"));
        recruitFieldCodes.add(new RecruitFieldCode("07", "영업ㆍ판매ㆍ운전ㆍ운송직"));
        recruitFieldCodes.add(new RecruitFieldCode("08", "건설ㆍ채굴직"));
        recruitFieldCodes.add(new RecruitFieldCode("09", "설치ㆍ정비ㆍ생산직"));
        recruitFieldCodes.add(new RecruitFieldCode("10", "농림어업직"));
        return recruitFieldCodes;
    }
	
	@ModelAttribute("academicRecordCodes")
    public List<AcademicRecordCode> academicRecordCodes() {
        List<AcademicRecordCode> academicRecordCode = new ArrayList<>();
        academicRecordCode.add(new AcademicRecordCode("00", "학력무관"));
        academicRecordCode.add(new AcademicRecordCode("01", "초졸이하"));
        academicRecordCode.add(new AcademicRecordCode("02", "중졸"));
        academicRecordCode.add(new AcademicRecordCode("03", "고졸"));
        academicRecordCode.add(new AcademicRecordCode("04", "대졸(2~3년)"));
        academicRecordCode.add(new AcademicRecordCode("05", "대졸(4년)"));
        academicRecordCode.add(new AcademicRecordCode("06", "석사"));
        academicRecordCode.add(new AcademicRecordCode("07", "박사"));
        return academicRecordCode;
    }
	
	@ModelAttribute("careerCodes")
    public List<CareerCode> careerCodes() {
        List<CareerCode> careerCode = new ArrayList<>();
        careerCode.add(new CareerCode("01", "무관"));
        careerCode.add(new CareerCode("02", "신입"));
        careerCode.add(new CareerCode("03", "경력"));
        careerCode.add(new CareerCode("04", "신입/경력"));
        return careerCode;
    }
	
	@ModelAttribute("militaryStatuses")
	public MilitaryStatus[] militaryStatuses() {
		return MilitaryStatus.values();
	}
	
	@ModelAttribute("maritalStatuses")
	public MaritalStatus[] maritalStatuses() {
		return MaritalStatus.values();
	}
	
	@ModelAttribute("readingCodes")
	public ReadingCode[] readingCodes() {
		return ReadingCode.values();
	}
	
}
