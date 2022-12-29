package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
	
}
