package sample.project.jobissue.repository;



import java.util.List;

import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.ResumeItem;
import sample.project.jobissue.domain.UserVO;

public interface ResumeRepository {
	
	public ResumeItem insertResume(ResumeItem resumeItem);

	public ResumeItem selectByUserCode(int userCode);
	
	public List<JobItem> selectBySubmit(int userCode);
	
	public void insertMulEmp(@Param("announcementCode") int announcementCode, @Param("empOptions") List<String> options );

	public void insertMulWork(@Param("announcementCode") int announcementCode, @Param("workOptions") List<String> options );

	public void insertMulAca(@Param("announcementCode") int announcementCode, @Param("acaOptions") List<String> options );
	
	public JobItem selectByAnnCode(int submitListAnnCode);
	
	public boolean deleteSubmitResume(int userCode, int submitListAnnCode);
	
	
	
	
	public boolean update(int userCode, ResumeItem resumeItem);
	
	public boolean deleteResume(int userCode,  ResumeItem resumeItem);
	
	public boolean insertAfter(int userCode, UserVO userVO);
	
	public boolean deleteAfter(int userCode, UserVO userVO);
}
