package sample.project.jobissue.domain;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.Getter;

@Data
public class JobItem {
	private int announcementCode;
	private int corCode;
	private String corName;
	private String announcement;
	private String recruitFieldCode;
	private String recruitField;
	
	private List<String> employTypeCode;
	private List<String> employType;
	
	private String salary;
	
	private List<String> workingAreaCode;
	private List<String> workingArea;
	
	private String careerCode;
	private String career;
	
	private String recruitPerson;
	
	private List<String> academicRecordCode;
	private List<String> academicRecord;
	
	private LocalDate receiptStartDate;
	private LocalDate receiptEndDate;
	
	private LocalDate recruitWritedate;
	
	
}
