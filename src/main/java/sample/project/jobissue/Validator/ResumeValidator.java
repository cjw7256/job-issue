//package sample.project.jobissue.Validator;
//
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//import sample.project.jobissue.domain.ResumeItem;
//import sample.project.jobissue.domain.UserInfo;
//
//@Component
//public class ResumeValidator implements Validator {
//
//	@Override
//	public boolean supports(Class<?> clazz) {
//		// TODO Auto-generated method stub
//		return UserInfo.class.isAssignableFrom(clazz);
//	}
//
//	@Override
//	public void validate(Object target, Errors errors) {
//		// TODO Auto-generated method stub
//		
//		ResumeItem resumeItem = (ResumeItem)target;
//		
//		if(!StringUtils.hasText(resumeItem.getResumeTitle())) {
//			errors.rejectValue("loginId", null, "제목은 필수 입력입니다.");			
//		}
//		if(!StringUtils.hasText(resumeItem.getUserResumeEmail())) {
//			errors.rejectValue("password", null, "이메일은 필수 입력입니다.");
//		}
//	}	
//}