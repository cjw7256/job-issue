package sample.project.jobissue.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.PreRecruitment;
import sample.project.jobissue.domain.UserVO;

public interface AdminRepository { //실제 db 연결

	public List<UserVO> selectUserInfoList();
	
	public List<UserVO> selectCorUserInfoList();
	
	public List<PreRecruitment> selectPreAll();
	
	public PreRecruitment selectPreByAnnCode(int annCode);
	
	public List<String> selectPreEmployType(int annCode);
	
	public List<String> selectPreWorkingArea(int annCode);
	
	public List<String> selectPreAcademicRecord(int annCode);
	
	//
	
	public PreRecruitment insertPreToRecru(PreRecruitment preRecruitment);
	
	public void insertPreToMulEmp(@Param("announcementCode") int announcementCode, @Param("empOptions") List<String> options );

	public void insertPreToMulWork(@Param("announcementCode") int announcementCode, @Param("workOptions") List<String> options );

	public void insertPreToMulAca(@Param("announcementCode") int announcementCode, @Param("acaOptions") List<String> options );

	//update
	
	public boolean updatePreStat(String applyStat, int announcementCode);
	
	//변경부분
	public void deleteRecByAdmin(int annCode);
	
	public Integer insertRecruToDel(JobItem jobItem);
	
	public UserVO selectUserDetailInfo(int userCode);
	
	public void deleteUserByAdmin(int userCode, String resumeCode); 
	
	public UserVO selectCorDetailInfo(int userCode);
	
}
