package sample.project.jobissue.domain;



import lombok.Data;

@Data
public class ResumeItem {
	public int userCode;				//회원 코드
	public String UserResumeEmail;		//회원이메일
	public String MilitaryStatus;		//병역
	public String MaritalStatus;		//결혼여부
	public String EmployTypeCode;		//공고형태 코드
	public String RecruitFieldCode;		//모집분야 코드
	public String ResumeTitle;			//이력서 제목
	public String AcademicRecordCode;	//학력 코드
	public String CareerCode;			//경력 코드
	public String CoverLetter;			//자기소개서
	public String License;				//자격증
	public String Activity;				//대외활동
	public String Portfolio;			//포트폴리오
	public String ReadingCode;			//열람여부(공개/비공개)
}
