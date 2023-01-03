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
import sample.project.jobissue.session.SessionManager;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/adminPage")
public class AdminController {

	private final JobRepository jobRepository;
	private final AdminRepository adminRepository;

	@GetMapping
	public String mainAdminPage(Model model, HttpServletRequest req, HttpServletResponse resp) { //관리자 페이지 처음 들어왔을 때 보이는 화면->무엇을 보이게 할 것인지?
		HttpSession session = req.getSession(false);
		PrintWriter w;

		if(session == null) { //로그인 안 된 경우(로그인 정보 조회 불가).. 경고창을 좀 띄우고 싶긴 하네요..
			//->알림창 띄우고 싶었던 작은... 소망
			//			try {
			//				log.info("session null");
			//				resp.setContentType("text/html; charset=utf-8");
			//				w = resp.getWriter();
			//				w.write("<script>alert('"+"먼저 로그인 해주세요!"+"')</script>");
			//		        w.close();
			//		        
			//		        return "redirect:/lists/lists";
			//			} catch (IOException e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//				
			//				log.info("session null 오류");
			//				return "redirect:/";
			//			}
			return "redirect:/user/login"; //->임시로 막아둠

		}

		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		if(!userVO.getUserType().equals("0")) { //로그인 했으나 관리자가 아닌 경우
			//->알림창 띄우고 싶었던 작은... 소망
			//			resp.setContentType("text/html; charset=utf-8");
			//			try {
			//				w = resp.getWriter();
			//				w.write("<script>alert('"+"접근 권한이 없습니다."+"')</script>");
			//		        w.close();
			//		        
			//				return "redirect:/";
			//				
			//			} catch (IOException e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//				return "redirect:/";
			//			}
			return "redirect:/";
		}
		log.info("admin info {}", userVO.getUserType()); //->임시로 막아둠

		List<PreRecruitment>preRecList = adminRepository.selectPreAll(); //최근 승인 대기 공고 테이블을 위한 데이터 넘김

		List<UserVO> corUserList = adminRepository.selectCorUserInfoList(); //userinfo쪽에서 받아오는 회원 정보를 출력하기 위해 데이터 넘김

		List<UserVO> userList = adminRepository.selectUserInfoList();

		model.addAttribute("user", userVO);
		model.addAttribute("preRecList", preRecList);
		model.addAttribute("corUserList", corUserList);
		model.addAttribute("userList", userList);

		return "/admin/adminMain";
	}

	@GetMapping("/applyRec")
	public String applyAnnPage(Model model) { //공고 신청 리스트가 보이는 화면 - 승인을 기다리는 공고 리스트가 보임

		List<PreRecruitment>preRecList = adminRepository.selectPreAll(); //최근 승인 대기 공고 테이블을 위한 데이터 넘김

		List<UserVO> corUserList = adminRepository.selectCorUserInfoList(); //userinfo쪽에서 받아오는 회원 정보를 출력하기 위해 데이터 넘김

		List<UserVO> userList = adminRepository.selectUserInfoList();

		model.addAttribute("preRecList", preRecList);
		model.addAttribute("corUserList", corUserList);
		model.addAttribute("userList", userList);

		return "/admin/applyRecruit";
	}

	//공고 승인 - 공고 상세 페이지
	@GetMapping("/applyRec/{announcementCode}")
	public String applyAnnDetailPage(Model model, @PathVariable("announcementCode") int annCode) {

		//특정 공고
		PreRecruitment preRec = adminRepository.selectPreByAnnCode(annCode);

		log.info("preRec {}", preRec);
		model.addAttribute("preRec", preRec);

		return "/admin/adminDetail";
	}

	@Transactional
	@PostMapping("/applyRec/applyPost") //공고 승인 처리
	public String applyAnnPostPage(@RequestParam int annCode) {
		PreRecruitment preRec = adminRepository.selectPreByAnnCode(annCode); //공고 번호로 임시 저장된 공고 찾기

		log.info("applyAnnPostPage pre {}", preRec);

		preRec.setRecruitField("01"); //값이 너무 커서 안 들어간대서 임시로 저장

		//임시 저장된 공고 저장하기
		adminRepository.insertPreToRecru(preRec);

		adminRepository.insertPreToMulEmp(annCode, preRec.getEmployType());
		adminRepository.insertPreToMulWork(annCode, preRec.getWorkingArea());
		adminRepository.insertPreToMulAca(annCode, preRec.getAcademicRecord());

		log.info("applyAnnPostPage 임시 저장된 공고 -> 승인 완료");

		//임시 저장 공고 승인 코드 변경하기~
		preRec.setApplyStat("01"); //이쪽 경로로 들어오면 stat을 01로 바꿔줌(승인되었다는 거니까)

		adminRepository.updatePreStat(preRec.getApplyStat(), annCode);

		log.info("applyAnnPostPage 승인코드 변경 완료");

		return "redirect:/adminPage/applyRec";
	}


	@PostMapping("/applyRec/rejPost") //공고 거절 처리
	public String rejectAnnPostPage(@RequestParam int annCode) { 

		log.info("rej {}", annCode);	

		PreRecruitment preRec = adminRepository.selectPreByAnnCode(annCode); //공고 번호로 임시 저장된 공고 찾기

		log.info("applyAnnPostPage 임시 저장된 공고 -> 거절");

		//임시 저장 공고 승인 코드 변경 => 거절

		preRec.setApplyStat("02");

		adminRepository.updatePreStat(preRec.getApplyStat(), annCode);

		return "redirect:/adminPage/applyRec";
	}

	//공고 삭제 페이지 - 현재 게재된 공고리스트가 뜸
	@GetMapping("/deleteRec")
	public String closedAnnPage(Model model) { 
		log.info("공고 삭제 페이지");
		List<JobItem> recAllList = jobRepository.selectAll(); //최근 승인 대기 공고 테이블을 위한 데이터 넘김

		log.info("{}", recAllList.get(0));

		model.addAttribute("recAllList", recAllList);

		return "/admin/closedAnn";
	}

	//공고 삭제 - 공고 상세 페이지
	@GetMapping("/deleteRec/{announcementCode}")
	public String closedAnnDetailPage(Model model, @PathVariable("announcementCode") int annCode) {
		//특정 공고 상세 페이지 정보 띄우기
		JobItem delRec = jobRepository.selectByAnnCode(annCode);

		log.info("deleteRec {}", delRec);
		model.addAttribute("delRec", delRec);

		return "/admin/delAdminDetail";
	}

	//공고 삭제 페이지 - 공고 삭제 처리
	@PostMapping("/deleteRec/delPost")
	public String closedAnnPostPage(@RequestParam int annCode) { 
		log.info("annCode {}", annCode);

		//공고 삭제 처리 메소드 - 넘어온 공고번호를 이용, 우선 해당 객체를 찾음
		JobItem jobItem = jobRepository.selectByAnnCode(annCode);

		//위의 객체를 파라미터로 넘겨서, rec -> del로 정보 추가 
		adminRepository.insertRecruToDel(jobItem);

		//마찬가지로 넘어온 공고번호를 이용, rec에서 삭제
		adminRepository.deleteRecByAdmin(annCode);

		log.info("공고 삭제 처리 완료");

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
		public String delUserDetail(@RequestParam("userCode") int userCode, @RequestParam("corCode") int corCode) { 
			log.info("corCode {}", corCode);

			//메소드 실행 -> corporation info , user_info, recruitment, pre~ 전부 지워져야 함
			adminRepository.deleteCorUserByAdmin(userCode, corCode);

			//recru 삭제
			List<Integer> annCodes =  adminRepository.selectRecCodes(corCode);
			
			if(!annCodes.isEmpty() & annCodes != null) {
				for(int annCode : annCodes) {
					adminRepository.deleteRecByAdmin(annCode); //돌면서 annCode를 넣어주면서 삭제할 수 있게!
				}
			}
			
			//pre~ 삭제
			List<Integer> preAnnCodes = adminRepository.selectPreRecCodes(corCode);
			
			if(!preAnnCodes.isEmpty() & preAnnCodes != null) {
				for(int preAnnCode : preAnnCodes) {
					adminRepository.deletePreRecByAdmin(preAnnCode); //돌면서 annCode를 넣어주면서 삭제할 수 있게!
				}
			}
			
			log.info("기업 회원정보 삭제 처리 완료");

			return "redirect:/adminPage/manageCor";
		}




}
