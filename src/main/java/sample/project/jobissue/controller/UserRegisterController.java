package sample.project.jobissue.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.service.UserService;
import sample.project.jobissue.validation.LoginForm;
import sample.project.jobissue.validation.UserRegisterForm;


@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserService userService;

    // 회원가입 페이지
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerGET(Model model) throws Exception {
    	
    	UserRegisterForm userRegisterForm = new UserRegisterForm();
    	model.addAttribute("userRegisterForm", userRegisterForm);
    	
        return "/user/register";
    }

    // 회원가입 처리
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPOST(@ModelAttribute UserRegisterForm userRegisterForm, RedirectAttributes redirectAttributes
    		, BindingResult bindingResult) throws Exception {
    	log.info("register userRegisterForm : {}", userRegisterForm);
    	
    	validateUserRegister(userRegisterForm, bindingResult);
    	
    	if(bindingResult.hasErrors()) {
    		return "/user/register";
    	}
    	
    	userService.register(userRegisterForm);
        redirectAttributes.addAttribute("msg", "REGISTERED");

        return "redirect:/user/login";
    }
    
	public void validateUserRegister(UserRegisterForm userRegisterForm, Errors errors) {
		
		if(!StringUtils.hasText(userRegisterForm.getUserEmail())) {
			errors.rejectValue("userEmail", null, "아이디를 입력하세요.");
		}
		
		if(!StringUtils.hasText(userRegisterForm.getUserPassword())) {
			errors.rejectValue("userPassword", null, "비밀번호를 입력하세요.");
		}
		
		if(!StringUtils.hasText(userRegisterForm.getUserName())) {
			errors.rejectValue("userName", null, "이름을 입력하세요.");
		}
		
		if(!StringUtils.hasText(userRegisterForm.getUserPasswordConfirm())) {
			errors.rejectValue("userPasswordConfirm", null, "비밀번호 확인을 입력하세요.");
		}
		
		if( !(userRegisterForm.getUserPassword().equals(userRegisterForm.getUserPasswordConfirm())) ) {
			errors.rejectValue("userPasswordConfirm", null, "비밀번호가 일치하지 않습니다.");
		}
		
		if(!StringUtils.hasText(userRegisterForm.getUserBirth())) {
			errors.rejectValue("userBirth", null, "생년월일을 입력하세요.");
		}
		
		if(userRegisterForm.getUserBirth().length()>8) {
    		userRegisterForm.setUserBirth(userRegisterForm.getUserBirth().replace("-", ""));
    		
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    		LocalDate ldNow = LocalDate.now();
    		LocalDate ldInput = LocalDate.parse(userRegisterForm.getUserBirth(), formatter);
    		log.info("ldNow {}, input {}", ldNow, ldInput);
    		if(ldNow.isBefore(ldInput)) {
    			errors.rejectValue("userBirth", null, "생년월일을 확인하세요.");
    		}
//    		ldNow
    	}
		
    	
		
		if(!StringUtils.hasText(userRegisterForm.getUserTel())) {
			errors.rejectValue("userTel", null, "전화번호를 입력하세요.");
		}
		
		if(!StringUtils.hasText(userRegisterForm.getUserAddress())) {
			errors.rejectValue("userAddress", null, "주소를 입력하세요.");
		}
		
		if(!StringUtils.hasText(userRegisterForm.getUserGender()) || userRegisterForm.getUserGender().equals("N")) {
			errors.rejectValue("userGender", null, "성별을 선택하세요.");
		}
		
		if(StringUtils.hasText(userRegisterForm.getUserTel())) {
			Pattern pattern = Pattern.compile("\\d{3}-\\d{3,4}-\\d{4}");
			Matcher matcher = pattern.matcher(userRegisterForm.getUserTel());
			if (!matcher.matches()) {
				errors.rejectValue("userTel", null, "전화번호를 확인하세요.");
			} 
		}
		
		
		if(StringUtils.hasText(userRegisterForm.getUserEmail())) {
			try {
				if( userService.findUserByEmail(userRegisterForm.getUserEmail()) != null ) {
					errors.rejectValue("userEmail", null, "이미 가입된 아이디입니다.");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    
}
