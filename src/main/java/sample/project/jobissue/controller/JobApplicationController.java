package sample.project.jobissue.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.AcademicRecordCode;
import sample.project.jobissue.domain.CareerCode;
import sample.project.jobissue.domain.EmployTypeCode;
import sample.project.jobissue.domain.FileStoreDto;
import sample.project.jobissue.domain.FileTypeCode;
import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.MaritalStatus;
import sample.project.jobissue.domain.MilitaryStatus;
import sample.project.jobissue.domain.PageMaker;
import sample.project.jobissue.domain.ReadingCode;
import sample.project.jobissue.domain.RecruitFieldCode;
import sample.project.jobissue.domain.ResumeItem;
import sample.project.jobissue.domain.SearchItem;
import sample.project.jobissue.domain.SubmitResumeItem;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.repository.FileStoreRepository;
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
	private final FileStoreRepository fileStoreRepository; //???????????? Repository

	//???????????? ????????? ?????? model ??????
			@ModelAttribute("pageMaker")
			public PageMaker searchItem(SearchItem si) {
				if (si == null) {
					si = new SearchItem();
				}
				PageMaker pageMaker = new PageMaker(si);
				
				return pageMaker;
			}

	
	@PostMapping("/checkSession")
	public String checkSession(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {

		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO) session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		ResumeItem resumeItem = jobApplicationRepository.selectByUserResume(userVO.getUserCode());
		model.addAttribute("submitResume", resumeItem);
		
		if(resumeItem != null) {
			if (session.getAttribute(SessionManager.SESSION_COOKIE_NAME) != null) {
				return "redirect:/submit/" + userVO.getUserCode();
			}
		}else {
			resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<script>");
            out.println("alert('???????????? ?????? ???????????????.');");
            out.println("window.location = '/resumes/insert';");
            out.println("</script>");
            out.flush();
            out.close();
            return null;
		}
		
		
		return "redirect:/user/login";
	}

	@GetMapping("/{resumeUserCode}")
	public String submitResume(Model model, @PathVariable("resumeUserCode") int resumeUserCode,
			HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO) session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		ResumeItem resumeItem = new ResumeItem();
		resumeItem.setUserCode(userVO.getUserCode());
		resumeItem = jobApplicationRepository.selectByUserResume(resumeUserCode);
		model.addAttribute("submitResume", resumeItem);
		FileStoreDto fileStoreDto = fileStoreRepository.selectFileInfo(FileTypeCode.TB_CODE_RESUME, String.valueOf(resumeItem.getUserCode()));
		model.addAttribute("fileInfo", fileStoreDto);
		
		
		return "submit/submitResume";
	}

	@GetMapping("/dosubmit/{userCode}")
	public String doSubmit(Model model,
			HttpServletRequest req,
			HttpServletResponse resp) throws IOException {

		HttpSession session = req.getSession(false);
		JobItem jobItem = (JobItem) session.getAttribute("corCord");
		jobRepository.selectByAnnCode(jobItem.getAnnouncementCode());
		session = req.getSession(false);
		UserVO userVO = (UserVO) session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		SubmitResumeItem submitResumeItem =
				jobApplicationRepository.selectByUserSubmitResume
				(userVO.getUserCode(), jobItem.getAnnouncementCode());
		
		if(submitResumeItem!=null) {
			resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<script>");
            out.println("alert('?????? ????????? ???????????????.');");
            out.println("window.location = '/resumes/submitLists';");
            out.println("</script>");
            out.flush();
            out.close();
            return null;
		}
		else {
			jobApplicationRepository.insertSubmitResume(jobItem.getCorCode(), jobItem.getAnnouncementCode(),
					userVO.getUserCode());
			return "redirect:/resumes/submitLists";	
		}
		
		
	}

	@ModelAttribute("employTypeCodes")
	public List<EmployTypeCode> employTypeCodes() {
		List<EmployTypeCode> employTypeCodes = new ArrayList<>();
		employTypeCodes.add(new EmployTypeCode("01", "?????????"));
		employTypeCodes.add(new EmployTypeCode("02", "?????????"));
		employTypeCodes.add(new EmployTypeCode("03", "?????????"));
		employTypeCodes.add(new EmployTypeCode("04", "????????????"));
		employTypeCodes.add(new EmployTypeCode("05", "??????/???????????????"));
		return employTypeCodes;
	}

	@ModelAttribute("recruitFieldCodes")
	public List<RecruitFieldCode> recruitFieldCodes() {
		List<RecruitFieldCode> recruitFieldCodes = new ArrayList<>();
		recruitFieldCodes.add(new RecruitFieldCode("01", "????????????????????????????????????"));
		recruitFieldCodes.add(new RecruitFieldCode("02", "????????? ??? ?????? ?????????"));
		recruitFieldCodes.add(new RecruitFieldCode("03", "??????????????????????????????????????????????????? ??? ??????"));
		recruitFieldCodes.add(new RecruitFieldCode("04", "??????????????????"));
		recruitFieldCodes.add(new RecruitFieldCode("05", "??????????????????????????????????????????"));
		recruitFieldCodes.add(new RecruitFieldCode("06", "??????????????????????????????????????????????????????"));
		recruitFieldCodes.add(new RecruitFieldCode("07", "????????????????????????????????????"));
		recruitFieldCodes.add(new RecruitFieldCode("08", "??????????????????"));
		recruitFieldCodes.add(new RecruitFieldCode("09", "???????????????????????????"));
		recruitFieldCodes.add(new RecruitFieldCode("10", "???????????????"));
		return recruitFieldCodes;
	}

	@ModelAttribute("academicRecordCodes")
	public List<AcademicRecordCode> academicRecordCodes() {
		List<AcademicRecordCode> academicRecordCode = new ArrayList<>();
		academicRecordCode.add(new AcademicRecordCode("00", "????????????"));
		academicRecordCode.add(new AcademicRecordCode("01", "????????????"));
		academicRecordCode.add(new AcademicRecordCode("02", "??????"));
		academicRecordCode.add(new AcademicRecordCode("03", "??????"));
		academicRecordCode.add(new AcademicRecordCode("04", "??????(2~3???)"));
		academicRecordCode.add(new AcademicRecordCode("05", "??????(4???)"));
		academicRecordCode.add(new AcademicRecordCode("06", "??????"));
		academicRecordCode.add(new AcademicRecordCode("07", "??????"));
		return academicRecordCode;
	}

	@ModelAttribute("careerCodes")
	public List<CareerCode> careerCodes() {
		List<CareerCode> careerCode = new ArrayList<>();
		careerCode.add(new CareerCode("01", "??????"));
		careerCode.add(new CareerCode("02", "??????"));
		careerCode.add(new CareerCode("03", "??????"));
		careerCode.add(new CareerCode("04", "??????/??????"));
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