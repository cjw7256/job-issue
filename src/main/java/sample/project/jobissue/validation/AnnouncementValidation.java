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
//			errors.rejectValue("announcement",  "NotEmpty.preRecruit.announcement", "공고명은 필수 입력입니다.");
			errors.rejectValue("announcement", null, "공고명은 필수 입력입니다.");
		}
		if(!StringUtils.hasText(preRecruit.getSalary())) {
			errors.rejectValue("salary", null, "급여는 필수 입력입니다.");
		}
		if(!StringUtils.hasText(preRecruit.getRecruitPerson())) {
			errors.rejectValue("recruitPerson", null, "모집인원은 필수 입력입니다.");
		}
		if(!StringUtils.hasText(preRecruit.getRecruitFieldCode())) {
			errors.rejectValue("recruitFieldCode", null, "모집분야는 필수 입력입니다.");
		}
		if(!StringUtils.hasText(preRecruit.getCareerCode())) {
			errors.rejectValue("careerCode", null, "희망 경력은 필수 입력입니다.");
		}
		if(preRecruit.getPreEmployTypeCode().isEmpty()) {
			errors.rejectValue("preEmployTypeCode", null, "공고형태는 필수 입력입니다.");
		}
		if(preRecruit.getPreWorkingAreaCode().isEmpty()) {
			errors.rejectValue("preWorkingAreaCode", null, "근무 가능 지역은 필수 입력입니다.");
		}
		if(preRecruit.getPreAcademicRecordCode().isEmpty()) {
			errors.rejectValue("preAcademicRecordCode", null, "희망 학력은 필수 입력입니다.");
		}
		if(preRecruit.getEndDate() == null) {
			errors.rejectValue("endDate", null, "공고 게시 기간은 필수 입력입니다.");
		}

		
	}

}
