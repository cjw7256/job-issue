package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.PreRecruitment;

@Mapper
public interface PreRecruitmentMapper {

public Integer insertPreRecruit(PreRecruitment preRecruitment);
	
	public Integer insertCorInfo(String corName);
	
	public Integer insertMulEmp(@Param("announcementCode") int announcementCode, @Param("empOptions") List<String> options );

	public Integer insertMulWork(@Param("announcementCode") int announcementCode, @Param("workOptions") List<String> options );

	public Integer insertMulAca(@Param("announcementCode") int announcementCode, @Param("acaOptions") List<String> options );
	
	public List<String> selectEmployType(int listAnnCode);
	
	public List<String> selectWorkingArea(int listAnnCode);
	
	public List<String> selectAcademicRecord(int listAnnCode);
	
	public List<PreRecruitment> selectAll();
	
	public PreRecruitment selectByAnnCode(int listAnnCode);
	
//	public boolean update(int announcementCode , PreRecruitment preRecruitment);
//	
//	public void deletePreRecruitmentOptions(int announcementCode);
//	
	public void deleteAll();
}
