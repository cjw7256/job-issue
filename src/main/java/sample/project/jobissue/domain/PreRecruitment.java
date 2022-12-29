package sample.project.jobissue.domain;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class PreRecruitment {

   private int announcementCode; //공고 코드
   private int corCode;          //기업 코드
   private String corName; //기업명
   private String announcement;  //공고명
   private String recruitFieldCode; //모집분야 코드
   private String recruitField;  //모집분야
   
   private List<String> employTypeCode; //공고형태코드
   private List<String> employType;  //공고형태
   
   private String salary;  //급여
   
   private List<String> workingAreaCode;  //근무지역코드
   private List<String> workingArea; //근무지역
   
   private String careerCode;  //경력코드
   private String career;  //경력
   
   private String recruitPerson;  //모집인원
   
   private List<String> academicRecordCode;  //학력코드
   private List<String> academicRecord;  //학력
   
   private LocalDate receiptStartDate; //공고 시작일
   private LocalDate receiptEndDate; //공고 종료일
   
   private LocalDate recruitWriteDate; //공고 작성일
    
   private String applyStat; //공고형태
   

}