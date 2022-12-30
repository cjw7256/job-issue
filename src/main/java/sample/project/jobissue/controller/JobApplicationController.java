package sample.project.jobissue.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
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
import sample.project.jobissue.repository.JobRepository;
import sample.project.jobissue.session.SessionManager;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/submit")
public class JobApplicationController {

	private final JobApplicationRepository jobApplicationRepository;
	private final JobRepository jobRepository;
	
	@GetMapping("/{resumeUserCode}")
	public String submitResume(Model model
			, @PathVariable("resumeUserCode") int resumeUserCode
			, HttpServletRequest req) {
		log.info("17번");
		HttpSession session = req.getSession(false);
		log.info("18번");
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		log.info("19번");
		ResumeItem resumeItem = new ResumeItem();
		log.info("20번");
		resumeItem.setUserCode(userVO.getUserCode());
		log.info("21번");
		resumeItem = jobApplicationRepository.selectByUserResume(resumeUserCode);
		log.info("22번");
		model.addAttribute("submitResume", resumeItem);
		log.info("23번");
//		model.addAttribute("announcementCode", announcementCode);
		log.info("24번");
		log.info("레주메123 {}",resumeItem);
		log.info("25번");
		return "submit/submitResume";
	}
	

	
//	수정예정
	@GetMapping("/dosubmit/{userCode}")
	public String doSubmit(Model model
			, HttpServletRequest req) {
		log.info("26번");
//		JobItem jobItem2 = jobRepository.selectByAnnCode(jobItem.getAnnouncementCode());
		log.info("27번");
//		log.info("여기1 {}",jobItem);
		log.info("28번");
		HttpSession session = req.getSession(false);
		log.info("29번");
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		log.info("30번");
		jobApplicationRepository.insertSubmitResume(
				222, 62, userVO.getUserCode());
		
		log.info("제출성공");
				
		return "resumes/resumes";
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