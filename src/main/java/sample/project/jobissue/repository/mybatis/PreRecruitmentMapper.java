package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.PreRecruitment;

@Mapper
public interface PreRecruitmentMapper {

public Integer insertPreRecruit(PreRecruitment preRecruitment);
	
	public Integer insertCorInfo(String corName);
	
	public Integer insertPreMulEmp(@Param("announcementCode") int announcementCode, @Param("empOptions") List<String> options );

	public Integer insertPreMulWork(@Param("announcementCode") int announcementCode, @Param("workOptions") List<String> options );

	public Integer insertPreMulAca(@Param("announcementCode") int announcementCode, @Param("acaOptions") List<String> options );
	
	public List<String> selectEmployType(int listAnnCode);
	
	public List<String> selectWorkingArea(int listAnnCode);
	
	public List<String> selectAcademicRecord(int listAnnCode);
	
	public List<PreRecruitment> selectAll();
	
	public PreRecruitment selectByPreAnnCode(int listAnnCode);
	
	public List<PreRecruitment> selectByPreCorCode(int corCode);
	
	public boolean update(@Param("announcementCode")int announcementCode 
			, @Param("updateItem") PreRecruitment preRecruitment);

	public void deleteByAnnouncementCode(int announcementCode);
	
	public void deletePreMulEmp(int announcementCode);
	
	public void deletePreMulWork(int announcementCode);
	
	public void deletePreMulAca(int announcementCode);
	
	public void deleteAll();
}
