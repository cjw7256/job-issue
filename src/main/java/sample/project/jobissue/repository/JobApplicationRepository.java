package sample.project.jobissue.repository;

import sample.project.jobissue.domain.ResumeItem;

public interface JobApplicationRepository {
	public ResumeItem selectByUserResume(int userCode);
}
