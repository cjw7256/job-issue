package sample.project.jobissue.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class RejReasonInfo {
	public int announcementCode; //공고번호
	public String rejReasons; //거절 사유
	public int userCode; //관리자 정보(코드)
	public String adminName; //관리자 정보(관리자 이름)
	public LocalDate rejDate; //거절처리 일시
	
}
