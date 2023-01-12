package sample.project.jobissue.repository;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.ResumeItem;
import sample.project.jobissue.domain.SubmitResumeItem;

public interface JobApplicationRepository {
	public ResumeItem selectByUserResume(int userCode);
	
	public SubmitResumeItem selectByUserSubmitResume(int userCode, int announcementCode);
	
	public JobItem insertSubmitResume(int corCode, int announcementCode, int userCode);


}