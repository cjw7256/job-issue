package sample.project.jobissue.commons.interceptor;

import java.io.PrintWriter;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.session.SessionManager;

@Slf4j
public class UserAccInterceptor implements HandlerInterceptor{
	//개인 계정인지 체크 -> 개인 서비스 접근 제한
	
	private static final String LOGIN = "loginUser";
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String uri = request.getRequestURI();
		log.info("UserAccInterceptor preHandle {}", uri);
		
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute(LOGIN) == null) {
			log.info("로그인 정보 없음");
			
			response.sendRedirect("/user/login");
			return false;
		}else {
			UserVO userVO = (UserVO)session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
			
			if(!userVO.getUserType().equals("1")) {
				log.info("일반 사용자가 아닌 사용자가 접근");
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('일반(개인)회원만 사용가능한 페이지입니다!'); location.href='/';</script>");
//				response.sendRedirct("/");
				
				out.flush();
				out.close();
				
				return false;
			}else {
				log.info("{} 로그인 확인, 개인 페이지 접근", userVO.getUserName());
				
				return true;
			}
			
		}
	}
}
