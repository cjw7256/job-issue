package sample.project.jobissue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
// import sample.project.jobissue.domain.LoginDTO;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.service.UserService;
import sample.project.jobissue.session.SessionManager;
import sample.project.jobissue.validation.LoginForm;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor // 필드중에 final 키워드가 붙어있는 객체에다가 맞는걸 주입시켜줌
public class UserLoginController {

	private final UserService userService; // 초기화를 해줘야 함. 근데 Interface 임. 생성을 못함
											// 외부에서 어떤걸 쓸지 주입해줘야함.
	private final SessionManager sessionManager;

	// 로그인 페이지
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGET(Model model) {
		log.info("loginGET /login ");
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);
		return "/user/login";
	}

	// 로그인 처리
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPOST(@ModelAttribute LoginForm loginForm, HttpSession httpSession, Model model,
			HttpServletRequest req, HttpServletResponse resp, BindingResult bindingResult,
			@RequestParam(name = "redirectURL", defaultValue = "/") String redirectURL) throws Exception {

		validateLoginForm(loginForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "/user/login";
		}

		UserVO userVO = userService.login(loginForm);

		if (userVO == null) {
			bindingResult.reject("loginForm", "아이디 또는 비밀번호를 확인하세요.");
			return "/user/login";
		}

		sessionManager.create(userVO, resp);
		model.addAttribute("userVO", userVO);

		log.info("로그인 성공 : {} ", userVO);
		httpSession = req.getSession();
		httpSession.setAttribute(sessionManager.SESSION_COOKIE_NAME, userVO);

		return "redirect:" + redirectURL; // 로그인 후 보여줄 화면으로 연결
	}

	@RequestMapping("/logout")
	public String logout(HttpServletResponse resp, HttpServletRequest req) {
		HttpSession session = req.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		return "redirect:/";
	}

	public void validateLoginForm(LoginForm loginForm, Errors errors) {

		if (!StringUtils.hasText(loginForm.getUserEmail())) {
			errors.rejectValue("userEmail", null, "아이디를 입력하세요.");
		}

		if (!StringUtils.hasText(loginForm.getUserPassword())) {
			errors.rejectValue("userPassword", null, "비밀번호를 입력하세요.");
		}
	}

}
