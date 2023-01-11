package sample.project.jobissue.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.PageMaker;
import sample.project.jobissue.domain.SearchItem;
import sample.project.jobissue.domain.UserTypeCode;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.repository.AdminRepository;
import sample.project.jobissue.service.UserService;
import sample.project.jobissue.validation.PasswordForm;
import sample.project.jobissue.validation.UserRegisterForm;

@Controller
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class MyPageController {

	private final UserService userService;
	private final AdminRepository adminRepository;

	@RequestMapping(value = "/myPage", method = RequestMethod.GET)
	public String viewMyPage(HttpServletRequest request, Model model) throws Exception {

		HttpSession session = request.getSession(false);
		UserVO sessionUserVO = (UserVO) session.getAttribute("loginUser");

		log.info("userInfo {}", sessionUserVO);
		String loginUserEmail = sessionUserVO.getUserEmail();

		UserVO userVO = userService.findUserByEmail(loginUserEmail);
		model.addAttribute("userVO", userVO);

		if (userVO.getUserType().equals(UserTypeCode.USER_TYPE_PERSONAL)) {
			// 개인
			return "user/myPage";
		}
		if (userVO.getUserType().equals(UserTypeCode.USER_TYPE_CORPORATION)) {
			// 기업
			return "user/myPageCop";
		}
		if (userVO.getUserType().equals(UserTypeCode.USER_TYPE_ADMIN)) {
			// 관리자
			return "user/myPageAdmin";
		}

		return "user/myPage";
	}

	@PostMapping("/update")
	public String userUpdate(@ModelAttribute UserRegisterForm userRegisterForm, HttpServletRequest request, Model model)
			throws Exception {

		log.info("user_info {}", userRegisterForm);

		try {
			userService.modifyUserInfo(userRegisterForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/user/myPage";
	}

	@GetMapping("/passwordUpdate")
	public String passwordUpdatePage(HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession(false);
		UserVO sessionUserVO = (UserVO) session.getAttribute("loginUser");
		String loginUserEmail = sessionUserVO.getUserEmail();
		UserVO userVO = userService.findUserByEmail(loginUserEmail);
		model.addAttribute("userVO", userVO);
		return "user/passwordUpdate";
	}

	@PostMapping("/passwordUpdate")
	public String userPasswordUpdate(@ModelAttribute PasswordForm passwordForm, HttpServletRequest request,
			BindingResult bindingResult, Model model) throws Exception {
		HttpSession session = request.getSession(false);
		UserVO sessionUserVO = (UserVO) session.getAttribute("loginUser");
		try {
			// xhl@rms 아이디인 사람의 비밀번호를 바꾸어라
			// sessionUserVO.getUserPassword() 로 바꿔라.
			// 기존비번, 새로운비번

			validateUserPassword(passwordForm, bindingResult);
			if (bindingResult.hasErrors()) {
				return "/user/passwordUpdate";
			}
			if (sessionUserVO.getUserPassword().equals(passwordForm.getUserPassword())) {
				userService.userPasswordUpdate(passwordForm.getNewPassword(), sessionUserVO.getUserEmail());
			} else {
				return "/user/passwordUpdate";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/user/myPage";
	}

	@GetMapping("/leave/{userEmail}")
	public String userLeave(@PathVariable("userEmail") String userEmail, HttpServletRequest request, Model model)
			throws Exception {

		if (!StringUtils.hasText(userEmail)) {
			return "redirect:/user/myPage";
		}

		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loginUser");
		String curUserEmail = userVO.getUserEmail();

		if (!userEmail.equals(curUserEmail)) {
			return "redirect:/user/myPage";
		}

		UserVO curUserVO = userService.findUserByEmail(curUserEmail);
		UserRegisterForm userRegisterForm = new UserRegisterForm();
		userRegisterForm.setUserEmail(curUserEmail);
		model.addAttribute("userRegisterForm", userRegisterForm);

		return "/user/leave";
	}

	@PostMapping("/leave")
	public String userDrop(@ModelAttribute UserRegisterForm userRegisterForm, BindingResult bindingResult,
			HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loginUser");

		String curUserEmail = userVO.getUserEmail();
		String userEmail = userRegisterForm.getUserEmail();
		UserVO curUserVO = userService.findUserByEmail(userEmail);

		if (curUserVO == null || !(curUserVO.getUserEmail().equals(curUserEmail))) {
			return "redirect:/user/myPage";
		}
		Integer result;

		if (!curUserVO.getUserPassword().equals(userRegisterForm.getUserPassword())) {
			bindingResult.rejectValue("userPassword", null, "비밀번호를 확인하세요.");
		}

		if (bindingResult.hasErrors()) {
			return "user/leave";
		}
		if (userVO.getUserType().equals("1")) {
			if (curUserVO.getResumeCode().equals("Y")) {
				adminRepository.deleteResumeByDrop(curUserVO.getUserCode());
			}
		}

		if (userVO.getUserType().equals("2")) {

			List<Integer> annCodes = adminRepository.selectRecCodes(userVO.getCorCode());

			if (!annCodes.isEmpty() & annCodes != null) {
				for (int annCode : annCodes) {
					adminRepository.deleteRecByAdmin(annCode); // 돌면서 annCode를 넣어주면서 삭제할 수 있게!
				}
			}
			List<Integer> preAnnCodes = adminRepository.selectPreRecCodes(userVO.getCorCode());

			if (!preAnnCodes.isEmpty() & preAnnCodes != null) {
				for (int preAnnCode : preAnnCodes) {
					adminRepository.deletePreRecByAdmin(preAnnCode); // 돌면서 annCode를 넣어주면서 삭제할 수 있게!
				}
			}
		}

		result = userService.dropUserByEmail(userEmail);
		log.info("delete UserEmail : {}, Result : {}", userEmail, result);
		session.invalidate();

		return "redirect:/";
	}

	public void validateUserPassword(PasswordForm passwordForm, Errors errors) {

		if (!StringUtils.hasText(passwordForm.getUserPassword())) {
			errors.rejectValue("userPassword", null, "비밀번호를 입력하세요.");
		} else {
			// 입력한 경우
			// 사용가능한 비밀번호 제한
			Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$");
			Matcher matcher = pattern.matcher(passwordForm.getNewPassword());
			if (!matcher.matches()) {
				errors.rejectValue("userPassword", null, "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.");
			}
		}

		if (!StringUtils.hasText(passwordForm.getCheckPassword())) {
			errors.rejectValue("checkPassword", null, "비밀번호 확인을 입력하세요.");
		}

		if (!passwordForm.getNewPassword().equals(passwordForm.getCheckPassword())) {
			errors.rejectValue("newPassword", null, "비밀번호가 일치하지 않습니다.");
		}
	}

}
