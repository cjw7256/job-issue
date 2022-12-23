package sample.project.jobissue.repository;




import sample.project.jobissue.domain.ResumeItem;

public interface ResumeRepository {
	
	public ResumeItem insertResume(ResumeItem resumeItem);

	public ResumeItem selectByUserCode(int userCode);
	
	public boolean update(int userCode, ResumeItem resumeItem);
	
	public boolean deleteResume(int userCode,  ResumeItem resumeItem);
}
