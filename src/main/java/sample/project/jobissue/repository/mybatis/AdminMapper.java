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
	
	public List<UserVO> selCorForMain();
	
	public List<UserVO> selUserForMain();
	
	public List<PreRecruitment> selPreForMain();

	public PreRecruitment selectPreByAnnCode(int annCode);

	public List<String> selectPreEmployType(int annCode);

	public List<String> selectPreWorkingArea(int annCode);

	public List<String> selectPreAcademicRecord(int annCode);

	public UserVO selectUserDetailInfo(int userCode);

	public UserVO selectCorDetailInfo(int userCode);

	public List<Integer> selectRecCodes(int corCode); //announcemnet코드를 전부 넣음

	public List<Integer> selectPreRecCodes(int corCode);

	//insert

	public Integer insertPreToRecru(PreRecruitment preRecruitment);

	public Integer insertPreToMulEmp(@Param("announcementCode") int announcementCode, @Param("empOptions") List<String> options );

	public Integer insertPreToMulWork(@Param("announcementCode") int announcementCode, @Param("workOptions") List<String> options );

	public Integer insertPreToMulAca(@Param("announcementCode") int announcementCode, @Param("acaOptions") List<String> options );

	public Integer insertRecruToDel(JobItem jobItem);

	public Integer insertRecToDelEmp(@Param("announcementCode") int announcementCode, @Param("empOptions") List<String> options );

	public Integer insertRecToDelWork(@Param("announcementCode") int announcementCode, @Param("workOptions") List<String> options );

	public Integer insertRecToDelAca(@Param("announcementCode") int announcementCode, @Param("acaOptions") List<String> options);

	//update, delete

	public void updatePreStat(@Param("applyStat") String applyStat, @Param("announcementCode") int announcementCode); //수정

	public void deleteRecByAdmin(int annCode);

	public void deleteUserByAdmin(int userCode);

	public void deleteResumeByAdmin(int userCode);

	public void deleteEmpOptByAdmin(int annCode);

	public void deleteWorkOptByAdmin(int annCode);

	public void deleteAcaOptByAdmin(int annCode);

	public void deleteCorInfoByAdmin(int corCode);

	public void deletePreRecByAdmin(int annCode);

	public void deletePreEmpOptByAdmin(int annCode);

	public void deletePreWorkOptByAdmin(int annCode);

	public void deletePreAcaOptByAdmin(int annCode);


}
