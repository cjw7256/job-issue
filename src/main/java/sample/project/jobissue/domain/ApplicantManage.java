package sample.project.jobissue.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ApplicantManage {
	
	//공고
	private int corCode;          //기업 코드
	private String corName;		  //기업이름
	private String announcement;  //공고명
	private Integer announcementCode;  //공고코드
	private LocalDate receiptStartDate; //공고시작일
	private LocalDate receiptEndDate; //공고 종료일
	private String applyStat; //공고형태
	
	private List<ApplicantInfo> applicantInfos;
}
