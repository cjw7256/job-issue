package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.ResumeItem;

@Mapper
public interface JobMapper {

	public Integer insertRecruitInit(JobItem jobItem);
	
	public Integer insertCorInfo(String corName);
	
	public Integer insertMulEmp(@Param("announcementCode") int announcementCode, @Param("empOptions") List<String> options );

	public Integer insertMulWork(@Param("announcementCode") int announcementCode, @Param("workOptions") List<String> options );

	public Integer insertMulAca(@Param("announcementCode") int announcementCode, @Param("acaOptions") List<String> options );
	
	public List<String> selectEmployType(int listAnnCode);
	
	public List<String> selectWorkingArea(int listAnnCode);
	
	public List<String> selectAcademicRecord(int listAnnCode);
	
	public List<JobItem> selectAll();
	
	public JobItem selectByAnnCode(int listAnnCode);
	

}
