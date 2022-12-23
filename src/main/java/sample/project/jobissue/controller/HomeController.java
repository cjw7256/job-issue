package sample.project.jobissue.controller;

import java.util.Enumeration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.ResumeItem;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.session.SessionManager;

@Slf4j
@Controller
public class HomeController {

	@RequestMapping("/")
	public String homeMain(Model model
			,HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if(session == null) {
			return "index";
		}

		Enumeration<String> sessionName = session.getAttributeNames();
		while(sessionName.hasMoreElements()) {
			String name = sessionName.nextElement();
			log.info("session {}, {}", name, session.getAttribute(name));
		}
		
		log.info("{}, {}, {}, {}, {}"
				, session.getId()
				, session.getMaxInactiveInterval()
				, session.getCreationTime()
				, session.getLastAccessedTime()
				, session.isNew());
		
		UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
		log.info("userVO {}", userVO);
		if(userVO == null) {
			return "index";
		}
		ResumeItem resumeItem = new ResumeItem();
		log.info("userVO {}", userVO);
	 	resumeItem.setUserCode(userVO.getUserCode());
		log.info("resumeItem {}", resumeItem);
		log.info("userVO {}", userVO);
		model.addAttribute("resumeItem", resumeItem);
		model.addAttribute("userVO", userVO);
		return "index";
	}
}
