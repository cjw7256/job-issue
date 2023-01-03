package sample.project.jobissue.commons.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.session.SessionManager;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
	//extends vs implements 
	//상속	vs 	인터페이스 구현
	String LOGIN_MEMBER ="loginMember";
    private static final String LOGIN = "loginMember";
    
    private static final String[] blackList = {"/user/login"};

    
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String uri = request.getRequestURI();
		log.info("LogInterceptor preHandle {}, {}", uri);
		
		HttpSession session = request.getSession(false);

		if(PatternMatchUtils.simpleMatch(blackList, uri)) {
			if(session == null || session.getAttribute("loginUser") == null) {
				return true;
			} else {
				response.sendRedirect("/");
				return false;
			}
		} else {
			if(session == null || session.getAttribute("loginUser") == null) {
				log.info("로그인 없이 접근시도 {}", uri);
				response.sendRedirect("/user/login");
				return false;
			}		
		}
		
		return true;//진행
	}
}
