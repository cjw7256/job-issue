package sample.project.jobissue.domain;

import lombok.Data;

@Data
public class JobItem {
	public int CorporationNo;			//회사번호
	public String CorporationName;		//회사명
	public String Announcement;			//채용내용
	public String Salary;				//급여
	public String AnnouncementTypeCode;	//채용타입코드
	public String AnnouncementType;		//채용타입
	public String WorkingAreaCode;		//근무지코드
	public String WorkingArea;			//근무지
	public String CareerCode;			//경력코드
	public String Career;				//경력
	public String AcademicRecordCode;	//학력코드
	public String AcademicRecord;		//학력
	public String recruitmentCode;		//직종코드
	public String recruitment;			//직종
	public String RecruitmentPerson;	//모집인원
	public String receiptStart;			//접수시작
	public String receiptEnd;			//접수마감

}
