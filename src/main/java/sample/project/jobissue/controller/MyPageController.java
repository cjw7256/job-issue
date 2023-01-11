package sample.project.jobissue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.PageMaker;
import sample.project.jobissue.domain.SearchItem;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.repository.AdminRepository;
import sample.project.jobissue.repository.FileStoreRepository;
import sample.project.jobissue.service.UserService;
import sample.project.jobissue.validation.UserRegisterForm;

@Controller
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class MyPageController {

	private final UserService userService;
	private final AdminRepository adminRepository;
	private final FileStoreRepository fileStoreRepository;
	@RequestMapping(value = "/myPage", method = RequestMethod.GET)
	public String viewMyPage(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loginUser");

		log.info("userInfo {}", userVO);
		String UserEmail = userVO.getUserEmail();

		model.addAttribute("userVO", userVO);
//		UserVO mOne = mService.printOneById(memberId);
//		String post = mOne.getMemberAddress().split(",")[0];
//		String address = mOne.getMemberAddress().split(",")[1];

		return "user/myPage";

//		try {
//			mv.addObject("member", mOne);
//			mv.addObject("post", post);
//			mv.addObject("address", address);
//			mv.setViewName("member/myPage");
//
//		} catch (Exception e) {
//
//			mv.addObject("msg", e.getMessage());
//			mv.setViewName("common/errorPage");
//
//		}
//		return "/user/myPage";
	}

	@PostMapping("/update")
	public String userUpdate(@ModelAttribute UserRegisterForm userRegisterForm, HttpServletRequest request,
			Model model) throws Exception {

		log.info("user_info {}", userRegisterForm);

		try {
			userService.modifyUserInfo(userRegisterForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UserVO userVO = userService.findUserByEmail(userRegisterForm.getUserEmail());
		model.addAttribute("userVO", userVO);

		return "redirect:/user/myPage";
	}

	@GetMapping("/drop/{userEmail}")
	public String userDrop(@PathVariable("userEmail") String userEmail, HttpServletRequest request) {

		if (StringUtils.isEmpty(userEmail)) {
			return "/user/myPage";
		}

		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("loginUser");

		String curUserEmail = userVO.getUserEmail();

		Integer result;
		try {
			if (userEmail.equals(curUserEmail)) {
				if(userVO.getResumeCode().equals("Y")) {
					adminRepository.deleteResumeByDrop(userVO.getUserCode());
				}
				result = userService.dropUserByEmail(userEmail);
				log.info("delete UserEmail : {}, Result : {}", userEmail, result);
				session.invalidate();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileStoreRepository.deleteImage(userVO.getUserCode());
		return "redirect:/";
	}

}
