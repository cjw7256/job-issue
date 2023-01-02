package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.PreRecruitment;
import sample.project.jobissue.domain.UserVO;

@Mapper
public interface AdminMapper {
	public List<UserVO> selectUserInfoList();
	
	public List<UserVO> selectCorUserInfoList();
	
	public List<PreRecruitment> selectPreAll();
	
	public PreRecruitment selectPreByAnnCode(int annCode);
	
	public List<String> selectPreEmployType(int annCode);
	
	public List<String> selectPreWorkingArea(int annCode);
	
	public List<String> selectPreAcademicRecord(int annCode);
	
	//insert
	
	public Integer insertPreToRecru(PreRecruitment preRecruitment);
	
	public Integer insertPreToMulEmp(@Param("announcementCode") int announcementCode, @Param("empOptions") List<String> options );

	public Integer insertPreToMulWork(@Param("announcementCode") int announcementCode, @Param("workOptions") List<String> options );

	public Integer insertPreToMulAca(@Param("announcementCode") int announcementCode, @Param("acaOptions") List<String> options );

	//update, delete
	
	public void updatePreStat(@Param("applyStat") String applyStat, @Param("announcementCode") int announcementCode); //수정
	//변경부분~
	//delete 관련 쿼리문 아래쪽으로 추가함
	
		public void deleteRecByAdmin(int annCode);
		
		public Integer insertRecruToDel(JobItem jobItem);
		
		public Integer insertRecToDelEmp(@Param("announcementCode") int announcementCode, @Param("empOptions") List<String> options );
		
		public Integer insertRecToDelWork(@Param("announcementCode") int announcementCode, @Param("workOptions") List<String> options );
		
		public Integer insertRecToDelAca(@Param("announcementCode") int announcementCode, @Param("acaOptions") List<String> options);
		
		//추가된 select문, insert문
		public UserVO selectUserDetailInfo(int userCode);
		
		public void deleteUserByAdmin(int userCode);
		
		public void deleteResumeByAdmin(int userCode);
		
		public UserVO selectCorDetailInfo(int userCode);
}
