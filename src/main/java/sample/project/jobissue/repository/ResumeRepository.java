package sample.project.jobissue.repository;



import sample.project.jobissue.domain.ResumeItem;
import sample.project.jobissue.domain.UserVO;

public interface ResumeRepository {
	
	public ResumeItem insertResume(ResumeItem resumeItem);

	public ResumeItem selectByUserCode(int userCode);
	
	public boolean update(int userCode, ResumeItem resumeItem);
	
	public boolean deleteResume(int userCode,  ResumeItem resumeItem);
	
	public boolean insertAfter(int userCode, UserVO userVO);
	
	public boolean deleteAfter(int userCode, UserVO userVO);
}
