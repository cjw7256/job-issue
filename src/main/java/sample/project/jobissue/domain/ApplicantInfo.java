package sample.project.jobissue.domain;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class ApplicantInfo {
	//지원자 이력서
	public int announcementCode; 			//공고 코드
	public int userCode;					//회원 코드
	public String UserResumeEmail;			//회원이메일
	public MilitaryStatus MilitaryStatus;	//병역
	public String MaritalStatus;			//결혼여부
	public String EmployTypeCode;			//공고형태 코드
	public String EmployType;
	public String RecruitFieldCode;			//모집분야 코드
	public String RecruitField;
	public String ResumeTitle;				//이력서 제목
	public String AcademicRecordCode;		//학력 코드
	public String AcademicRecord;
	public String CareerCode;				//경력 코드
	public String Career;
	public String CoverLetter;				//자기소개서
	public String License;					//자격증
	public String Activity;					//대외활동
	public String Portfolio;				//포트폴리오
	public String ReadingCode;				//열람여부(공개/비공개)
	public LocalDate submitDate;					//이력서 제출 일자
	
	
	public String userName;
	public String userGender;
	public String userBirth;
	
	
}
