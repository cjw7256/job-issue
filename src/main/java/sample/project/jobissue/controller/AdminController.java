package sample.project.jobissue.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.PreRecruitment;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.repository.AdminRepository;
import sample.project.jobissue.repository.JobRepository;
import sample.project.jobissue.service.AdminService;
import sample.project.jobissue.session.SessionManager;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/adminPage")
public class AdminController {

	private final JobRepository jobRepository;
	private final AdminRepository adminRepository;
	private final AdminService adminService;

	@ModelAttribute("user")
	public UserVO userVO(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		
		UserVO userVO = new UserVO();
		
		if(session != null || session.getAttribute(SessionManager.SESSION_COOKIE_NAME) != null) {
			userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
			log.info("admin info {}", userVO.getUserType()); //->임시로 막아둠
		}
		
		log.info("user Model {}", userVO);
		return userVO;
	}
	
	@GetMapping
	public String mainAdminPage(Model model, HttpServletRequest req, HttpServletResponse resp) { //관리자 페이지 처음 들어왔을 때 보이는 화면->무엇을 보이게 할 것인지?
//		HttpSession session = req.getSession(false);
//		
//		UserVO userVO = new UserVO();
//		
//		if(session != null || session.getAttribute(SessionManager.SESSION_COOKIE_NAME) != null) {
//			userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
//			log.info("admin info {}", userVO.getUserType()); //->임시로 막아둠
//		}

		List<PreRecruitment>preRecList = adminRepository.selPreForMain(); //최근 승인 대기 공고 테이블을 위한 데이터 넘김

		List<UserVO> corUserList = adminRepository.selCorForMain(); //userinfo쪽에서 받아오는 회원 정보를 출력하기 위해 데이터 넘김

		List<UserVO> userList = adminRepository.selUserForMain();

//		model.addAttribute("user", userVO);
		model.addAttribute("preRecList", preRecList);
		model.addAttribute("corUserList", corUserList);
		model.addAttribute("userList", userList);

		return "/admin/adminMain";
	}
	
	//공고 신청 리스트가 보이는 화면 - 승인대기 공고 리스트가 보임
	@GetMapping("/applyRec")
	public String applyAnnPage(Model model) { 
		List<PreRecruitment>preRecList = adminRepository.selectPreAll(); //최근 승인 대기 공고 테이블을 위한 데이터 넘김

		model.addAttribute("preRecList", preRecList);
		
		return "/admin/applyRecruit";
	}

	//공고 승인 - 공고 상세 페이지
	@GetMapping("/applyRec/{announcementCode}")
	public String applyAnnDetailPage(Model model, @PathVariable("announcementCode") int annCode) {
		//특정 공고를 찾아오는 페이지
		PreRecruitment preRec = adminRepository.selectPreByAnnCode(annCode);

		log.info("preRec {}", preRec);
		model.addAttribute("preRec", preRec);

		return "/admin/adminDetail";
	}

	@Transactional
	@PostMapping("/applyRec/applyPost") //공고 승인 처리 페이지
	public String applyAnnPostPage(@RequestParam int annCode) {
		adminService.applyRec(annCode);
		return "redirect:/adminPage/applyRec";
	}

	@PostMapping("/applyRec/rejPost") //공고 거절 처리
	public String rejectAnnPostPage(@RequestParam int annCode) { 
		adminService.rejectRec(annCode);
		return "redirect:/adminPage/applyRec";
	}

	//공고 삭제 페이지 - 현재 게재된 공고리스트가 뜸
	@GetMapping("/deleteRec")
	public String closedAnnPage(Model model) { 
		log.info("공고 삭제 페이지");
		List<JobItem> recAllList = jobRepository.selectAll(); //최근 승인 대기 공고 테이블을 위한 데이터 넘김

		model.addAttribute("recAllList", recAllList);

		return "/admin/closedAnn";
	}

	//공고 삭제 - 공고 상세 페이지
	@GetMapping("/deleteRec/{announcementCode}")
	public String closedAnnDetailPage(Model model, @PathVariable("announcementCode") int annCode) {
		//특정 공고 상세 페이지 정보 띄우기
		JobItem delRec = jobRepository.selectByAnnCode(annCode);

		log.info("공고 상세 페이지 deleteRec {}", delRec);
		model.addAttribute("delRec", delRec);

		return "/admin/delAdminDetail";
	}

	//공고 삭제 페이지 - 공고 삭제 처리
	@PostMapping("/deleteRec/delPost")
	public String closedAnnPostPage(@RequestParam int annCode) { 
		adminService.deleteRec(annCode);
		return "redirect:/adminPage/deleteRec";
	}

	
	//회원 관리 페이지 - 일반 회원 리스트 보여줌
	@GetMapping("/manageUser")
	public String manageUserPage(Model model) {
		//쿼리문을 통해 회원 리스트를 가져옴
		List<UserVO> userInfos= adminRepository.selectUserInfoList();

		//모델을 통해 객체로 넘겨줌!
		model.addAttribute("userInfos", userInfos);

		return "/admin/manageUser";
	}

	//회원 관리 페이지 - 특정 회원의 정보를 보여줌
	@GetMapping("/manageUser/{userCode}")
	public String manageUserDetailPage(Model model, @PathVariable("userCode") int userCode) {
		//특정 회원의 상세 정보 페이지 띄우기 - 쿼리문 실행을 통해 유저 정보 가져오기
		UserVO userDetail = adminRepository.selectUserDetailInfo(userCode);

		//model에 담아 객체로 넘기기
		model.addAttribute("userDetail", userDetail);

		return "/admin/manageUserDetail";
	}

	//회원 관리 페이지 - 회원 정보 삭제 처리
	@PostMapping("/manageUser/delInfo")
	public String delUserDetail(@RequestParam("userCode") int userCode, @RequestParam("resumeCode") String resumeCode) { 
		log.info("userCode {}, resumeCode {}", userCode, resumeCode);

		//메소드 실행
		adminRepository.deleteUserByAdmin(userCode, resumeCode);

		log.info("회원정보 삭제 처리 완료");

		return "redirect:/adminPage/manageUser";
	}

	//회원 관리 페이지 - 기업 회원 리스트 보여줌
	@GetMapping("/manageCor")
	public String manageCorPage(Model model) {
		//쿼리문을 통해 회원 리스트를 가져옴
		List<UserVO> corInfos= adminRepository.selectCorUserInfoList();

		//모델을 통해 객체로 넘겨줌!
		model.addAttribute("corInfos", corInfos);

		return "/admin/manageCor";
	}

	//회원 관리 페이지 - 특정 기업의 정보를 보여줌
	@GetMapping("/manageCor/{userCode}")
	public String manageCorDetailPage(Model model, @PathVariable("userCode") int userCode) {
		//특정 회원의 상세 정보 페이지 띄우기 - 쿼리문 실행을 통해 유저 정보 가져오기
		UserVO corDetail = adminRepository.selectCorDetailInfo(userCode);

		//model에 담아 객체로 넘기기
		model.addAttribute("corDetail", corDetail);

		return "/admin/manageCorDetail";
	}

	//회원 관리 페이지 - 기업 회원 정보 삭제 처리
		@PostMapping("/manageCor/delInfo")
		public String delCorDetail(@RequestParam("userCode") int userCode, @RequestParam("corCode") int corCode) { 
			log.info("corCode {}", corCode);

			adminService.deleteCorDetail(userCode, corCode);

			return "redirect:/adminPage/manageCor";
		}


}
