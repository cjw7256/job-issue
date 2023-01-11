package sample.project.jobissue.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sample.project.jobissue.domain.PreRecruitment;

@Component
public class AnnouncementValidation implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return PreRecruitment.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		PreRecruitment preRecruit = (PreRecruitment)target;

		if(!StringUtils.hasText(preRecruit.getAnnouncement())) {
			errors.rejectValue("announcement",  "NotEmpty.preRecruit.announcement", "공고명은 필수 입력입니다.");
		}
		if(!StringUtils.hasText(preRecruit.getSalary())) {
			errors.rejectValue("salary", null, "급여는 필수 입력입니다.");
		}
		if(!StringUtils.hasText(preRecruit.getRecruitPerson())) {
			errors.rejectValue("recruitPerson", null, "모집인원은 필수 입력입니다.");
		}

		
	}

}
