package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.ApplicantInfo;
import sample.project.jobissue.domain.ApplicantManage;
import sample.project.jobissue.domain.PreRecruitment;

@Mapper
public interface PreRecruitmentMapper {

	public Integer insertPreRecruit(PreRecruitment preRecruitment);
	
	public Integer insertPreMulEmp(@Param("announcementCode") int announcementCode, @Param("empOptions") List<String> options );

	public Integer insertPreMulWork(@Param("announcementCode") int announcementCode, @Param("workOptions") List<String> options );

	public Integer insertPreMulAca(@Param("announcementCode") int announcementCode, @Param("acaOptions") List<String> options );
	
	public List<String> selectEmployType(int listAnnCode);
	
	public List<String> selectWorkingArea(int listAnnCode);
	
	public List<String> selectAcademicRecord(int listAnnCode);
	
	public PreRecruitment selectByPreAnnCode(int annCode);
	
	public PreRecruitment selectByAnnCode(int annCode);
	
	public List<PreRecruitment> selectByPreCorCode(int corCode);
	
	public boolean update(@Param("announcementCode")int announcementCode 
			, @Param("updateItem") PreRecruitment preRecruitment);

	public void deletePreRecruitByAnnouncementCode(int announcementCode);
	
	public void deleteRecruitByAnnouncementCode(int announcementCode);
	
	public void deletePreMulEmp(int announcementCode);
	
	public void deletePreMulWork(int announcementCode);
	
	public void deletePreMulAca(int announcementCode);

	public List<ApplicantManage> selectByCorCode(int corCode);
	
	public ApplicantInfo userSelectByAnnCode(int announcementCode);

	public List<ApplicantInfo> selectByAnnSubmit(int announcementCode);
}
