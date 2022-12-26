package sample.project.jobissue.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.session.SessionManager;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/adminPage")
public class AdminController {
	
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
		model.addAttribute("user", userVO);

		return "/admin/adminMain";
	}
	
//	@GetMapping("")
//	public String applyAnnPage() { //공고 신청 리스트가 보이는 화면 - 승인을 기다리는 공고 리스트가 보임
//		
//		return "/admin/applyRecruit";
//	}
//	
//	@PostMapping("")
//	public String applyAnnPostPage() { //공고 승인 / 거절을 보냄
//		
//		return "redirect:/admin/applyRecruit";
//	}
//	
//	@GetMapping("")
//	public String closedAnnPage() { //공고 삭제 페이지 - 현재 게재된 공고리스트가 뜸
//		
//		return "/admin/closedAnn";
//	}
//	
//	@PostMapping("")
//	public String closedAnnPostPage() { //공고 삭제 페이지 - 공고 삭제 처리
//		
//		return "redirect:/admin/closedAnn";
//	}
	
	
	
	
}
