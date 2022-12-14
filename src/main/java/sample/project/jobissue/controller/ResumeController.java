package sample.project.jobissue.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
import sample.project.jobissue.domain.UploadFile;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.file.FileStoreManager;
import sample.project.jobissue.repository.FileStoreRepository;
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

	//???????????? ????????? ?????? model ??????
			@ModelAttribute("pageMaker")
			public PageMaker searchItem(SearchItem si) {
				if (si == null) {
					si = new SearchItem();
				}
				PageMaker pageMaker = new PageMaker(si);
				
				return pageMaker;
			}
	
	private final FileStoreManager fileStoreManager; //???????????????
	
	private final FileStoreRepository fileStoreRepository; //???????????? Repository
	
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
		SubmitResumeItem submitResumeItem = jobApplicationRepository.selectByUserSubmitResume(userVO.getUserCode(), submitListAnnCode);
		model.addAttribute("submitResume2", submitResumeItem);
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
		//?????? ????????? ?????? ????????????...
		FileStoreDto fileStoreDto = fileStoreRepository.selectFileInfo(FileTypeCode.TB_CODE_RESUME, String.valueOf(resumeItem.getUserCode()));
		model.addAttribute("fileInfo", fileStoreDto);
		
		return "/resumes/resume";
	}
	
	
	@GetMapping("/insert")
	public String newWrite(Model model
			, HttpServletRequest req
			, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		UserVO userVOInDB = userService.findUserByEmail(userVO.getUserEmail());
		if (userVOInDB.getResumeCode().equals("Y")) {
			resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<script>");
            out.println("alert('?????? ????????? ???????????? ????????????.');");
            out.println("window.location = '/resumes/resumes';");
            out.println("</script>");
            out.flush();
            out.close();
            return null;
		}
		ResumeItem resumeItem = new ResumeItem();
		model.addAttribute("resumeItem", resumeItem);
		return "resumes/insert";
	}
	
	@PostMapping("/insert")
	public String newWritePrecess(@ModelAttribute ResumeItem resumeItem
			, BindingResult bindingResult
			, HttpServletRequest req
			, HttpServletResponse resp
			) throws Exception {
		
		HttpSession session = req.getSession(false);
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		UserVO userVOInDB = userService.findUserByEmail(userVO.getUserEmail());
		resumeItem.setUserCode(userVO.getUserCode());
		resumeValidator.validate(resumeItem, bindingResult);
		
		if (bindingResult.hasErrors()) {
		log.info("error ??????");
		
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = resp.getWriter();

//			out.println("<script>alert('?????? ????????? ??????????????????!'); location.href='/resumes/insert';</script>");
			out.println("<script>alert('?????? ????????? ??????????????????!'); history.go(-1);</script>");
			
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			return "resumes/insert";
		}
		if (userVOInDB.getResumeCode().equals("N")) {
			resumeRepository.insertResume(resumeItem);
			resumeRepository.insertAfter(userVO.getUserCode(), userVO);
			try {
				//???????????? ?????? ???????????? ?????? ??????
				//1. ?????? ????????? ???????????? ??????
				UploadFile uploadFile = fileStoreManager.saveFile(resumeItem.profileImage);
				//2. ???????????? ??????????????? ???????????? ???????????? ??????(?????? ?????? item ?????? ????????? ??????)
				FileStoreDto fileStoreDto = new FileStoreDto();
				fileStoreDto.setFilename(uploadFile.getRealFileName());
				fileStoreDto.setUploadFilename(uploadFile.getOriginalFileName());
				fileStoreDto.setFiletype(FileTypeCode.IMAGE_FILE);
				fileStoreDto.setFilepath(fileStoreManager.getFilePath());
				fileStoreDto.setTableCode(FileTypeCode.TB_CODE_RESUME);
				fileStoreDto.setPkId(String.valueOf(resumeItem.getUserCode()));
				fileStoreRepository.insert(fileStoreDto);
			} catch (NullPointerException e) {
				// TODO: handle exception
				return "redirect:/resumes/resumes";	
			}
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
		fileStoreRepository.deleteImage(userVO.getUserCode());
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
		
		FileStoreDto fileStoreDto = fileStoreRepository.selectFileInfo(FileTypeCode.TB_CODE_RESUME, String.valueOf(resumeItem.getUserCode()));
		model.addAttribute("fileInfo", fileStoreDto);
		
		return "resumes/update";
	}
	
	@PostMapping("/update/{userCode}")
	public String updateResumeProcess(Model model
			, @PathVariable("userCode") int userCode
			, @ModelAttribute ResumeItem resumeItem
			, BindingResult bindingResult
			, HttpServletResponse resp) throws IllegalStateException, IOException {
		log.info("update post method {}", resumeItem);
		
		resumeValidator.validate(resumeItem, bindingResult);
		
		if (bindingResult.hasErrors()) {
			log.info("error ??????");
			 
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out;
			try {
				out = resp.getWriter();

				out.println("<script>alert('?????? ????????? ??????????????????!'); location.href='/resumes/update/"+userCode+"';</script>");
//				out.println("<script>alert('?????? ????????? ??????????????????'); history.go(-1);</script>");
				
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "redirect:/resumes/update/{userCode}";
//			return "resumes/update";
		}
		
		resumeRepository.update(userCode, resumeItem);
				
		
		try {
			//???????????? ?????? ???????????? ?????? ??????
			//1. ?????? ????????? ???????????? ??????
			UploadFile uploadFile = fileStoreManager.saveFile(resumeItem.profileImage);
			
			//2. ???????????? ??????????????? ???????????? ???????????? ??????(?????? ?????? item ?????? ????????? ??????)
			fileStoreRepository.deleteImage(userCode);
			FileStoreDto fileStoreDto = new FileStoreDto();
			fileStoreDto.setFilename(uploadFile.getRealFileName());
			fileStoreDto.setUploadFilename(uploadFile.getOriginalFileName());
			fileStoreDto.setFiletype(FileTypeCode.IMAGE_FILE);
			fileStoreDto.setFilepath(fileStoreManager.getFilePath());
			fileStoreDto.setTableCode(FileTypeCode.TB_CODE_RESUME);
			fileStoreDto.setPkId(String.valueOf(resumeItem.getUserCode()));
			fileStoreRepository.insert(fileStoreDto);

			
		} catch (NullPointerException e) {
			// TODO: handle exception
			return "redirect:/resumes/resumes/{userCode}";	
		}
		
		
		return "redirect:/resumes/resumes/{userCode}";	
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
