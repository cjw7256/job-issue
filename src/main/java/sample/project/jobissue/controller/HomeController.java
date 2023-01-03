package sample.project.jobissue.controller;

import java.util.Enumeration;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.PageMaker;
import sample.project.jobissue.domain.ResumeItem;
import sample.project.jobissue.domain.SearchItem;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.service.BoardService;
import sample.project.jobissue.session.SessionManager;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

	private final BoardService boardService;

	@RequestMapping("/")
	public String homeMain(Model model, HttpServletRequest req, SearchItem si) {

		if (si == null) {
			si = new SearchItem();
		}
		PageMaker pageMaker = new PageMaker(si);
		model.addAttribute("pageMaker", pageMaker);

		HttpSession session = req.getSession(false);
//		if(session == null) {
//			return "index";
//		}
		UserVO userVO = null;

		if (session != null) {
			Enumeration<String> sessionName = session.getAttributeNames();
			while (sessionName.hasMoreElements()) {
				String name = sessionName.nextElement();
				log.info("session {}, {}", name, session.getAttribute(name));
			}

			log.info("{}, {}, {}, {}, {}", session.getId(), session.getMaxInactiveInterval(), session.getCreationTime(),
					session.getLastAccessedTime(), session.isNew());
			
			userVO = (UserVO) session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
			log.info("userVO {}", userVO);
		}
		
		if (session == null) {
//			return "redirect:/";
		}

//		if(userVO == null) {
//			return "index";
//		}

		//리스트 데이터 가오져기
		List<JobItem> jobItemList = boardService.findHomeByName(si);
		//12개 만 가져오게.
		model.addAttribute("jobItemList", jobItemList);
		

		ResumeItem resumeItem = new ResumeItem();
		
		log.info("userVO {}", userVO);
//		resumeItem.setUserCode(userVO.getUserCode());
		log.info("resumeItem {}", resumeItem);
		log.info("userVO {}", userVO);
		log.info("ghrtl {}",jobItemList);
		model.addAttribute("resumeItem", resumeItem);
		model.addAttribute("userVO", userVO);
		return "index";
	}
	
	@RequestMapping("/search")
	public String homeMainSearch(Model model, HttpServletRequest req, SearchItem si) {
		if (si == null) {
			si = new SearchItem();
		}
		PageMaker pageMaker = new PageMaker(si);
		model.addAttribute("pageMaker", pageMaker);
		
		List<JobItem> jobItemList = boardService.findHomeByName(si);
		model.addAttribute("jobItemList", jobItemList);
		
		HttpSession session = req.getSession(false);
		if(session != null) {
			UserVO userVO = (UserVO) session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
			model.addAttribute("userVO", userVO);
		} else {
			model.addAttribute("userVO", null);	
		}
		
		
		
		return "search";
	}
}
