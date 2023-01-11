package sample.project.jobissue.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sample.project.jobissue.domain.ResumeItem;
import sample.project.jobissue.domain.UserVO;

@Component
public class ResumeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return UserVO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
		ResumeItem resumeItem = (ResumeItem)target;
		
		if(!StringUtils.hasText(resumeItem.getResumeTitle())) {
			errors.rejectValue("ResumeTitle", null, "제목은 필수 입력입니다.");			
		}
		if(!StringUtils.hasText(resumeItem.getUserResumeEmail())) {
			errors.rejectValue("UserResumeEmail", null, "이메일은 필수 입력입니다.");
		}
	}	
}
