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
		
		preRec.setRecruitField("01");
		preRec.setApplyStat("01");
		
		//임시 저장된 공고 저장하기
		adminRepository.insertPreToRecru(preRec);
		
		adminRepository.insertPreToMulEmp(annCode, preRec.getEmployType());
		adminRepository.insertPreToMulWork(annCode, preRec.getWorkingArea());
		adminRepository.insertPreToMulAca(annCode, preRec.getAcademicRecord());

		log.info("applyAnnPostPage 임시 저장된 공고 -> 승인 완료");
		
		//임시 저장 공고 승인 코드 변경하기~
		adminRepository.updatePreStat(preRec.getApplyStat(), annCode);
		
		log.info("applyAnnPostPage 승인코드 변경 완료");
		
		return "redirect:/adminPage/applyRec";
	}
	
	@PostMapping("/applyRec/rejPost") //공고 거절 처리
	public String rejectAnnPostPage(@RequestParam int annCode) { 

		log.info("rej {}", annCode);	
		
		PreRecruitment preRec = adminRepository.selectPreByAnnCode(annCode); //공고 번호로 임시 저장된 공고 찾기

		preRec.setApplyStat("02");
		
		return "redirect:/admin/applyRecruit";
	}
	
//	@GetMapping("/adminPage/deleteRec")
//	public String closedAnnPage() { //공고 삭제 페이지 - 현재 게재된 공고리스트가 뜸
//		
//		return "/admin/closedAnn";
//	}
//	
//	@PostMapping("/adminPage/deleteRec")
//	public String closedAnnPostPage() { //공고 삭제 페이지 - 공고 삭제 처리
//		
//		return "redirect:/admin/closedAnn";
//	}
	
	
	
	
}
