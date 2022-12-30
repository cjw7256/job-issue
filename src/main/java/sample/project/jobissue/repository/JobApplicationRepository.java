package sample.project.jobissue.repository;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.ResumeItem;

public interface JobApplicationRepository {
	public ResumeItem selectByUserResume(int userCode);
	
	
	public JobItem insertSubmitResume(int corCode, int announcementCode, int userCode);


}