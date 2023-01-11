package sample.project.jobissue.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.PreRecruitment;
import sample.project.jobissue.domain.RejReasonInfo;
import sample.project.jobissue.domain.UserVO;

public interface AdminRepository { //실제 db 연결

	public List<UserVO> selectUserInfoList();
	
	public List<UserVO> selectCorUserInfoList();
	
	public List<PreRecruitment> selectPreAll();
	
	public List<UserVO> selCorForMain();
	
	public List<UserVO> selUserForMain();
	
	public List<PreRecruitment> selPreForMain();
	
	public PreRecruitment selectPreByAnnCode(int annCode);
	
	public List<String> selectPreEmployType(int annCode);
	
	public List<String> selectPreWorkingArea(int annCode);
	
	public List<String> selectPreAcademicRecord(int annCode);
	
	public UserVO selectUserDetailInfo(int userCode);
	
	public UserVO selectCorDetailInfo(int userCode);
	
	public List<Integer> selectRecCodes(int corCode);
	
	public List<Integer> selectPreRecCodes(int corCode);
	
	//insert
	
	public PreRecruitment insertPreToRecru(PreRecruitment preRecruitment);
	
	public void insertPreToMulEmp(@Param("announcementCode") int announcementCode, @Param("empOptions") List<String> options );

	public void insertPreToMulWork(@Param("announcementCode") int announcementCode, @Param("workOptions") List<String> options );

	public void insertPreToMulAca(@Param("announcementCode") int announcementCode, @Param("acaOptions") List<String> options );

	public Integer insertRecruToDel(JobItem jobItem);
	
	//update
	
	public boolean updatePreStat(String applyStat, int announcementCode);
	
	//delete
	public void deleteRecByAdmin(int annCode);
	
	public void deleteUserByAdmin(int userCode, String resumeCode); 
	
		public void deleteCorUserByAdmin(int userCode, int corCode);
		
		public void deletePreRecByAdmin(int annCode);

		//회원 탈퇴 시 이력서만 지우는 메소드
		 public void deleteResumeByDrop(int userCode);
	
		 public Integer insRejReasonInfo(RejReasonInfo rejInfo);
		 
		 public List<RejReasonInfo> selectRejRecAll();
		 
		 public RejReasonInfo selectRejRec(int announcementCode);
		 
		 public Integer selectTotalDel();

			public Integer selectTotalUser();
			
			public Integer selectTotalCorUser();
}
