package sample.project.jobissue.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.ResumeItem;

public interface JobRepository {

	public JobItem insertRecruitInit(JobItem jobItem);
	
	public void insertCorInfo(String corName);
	
	public void insertMulEmp(@Param("announcementCode") int announcementCode, @Param("empOptions") List<String> options );

	public void insertMulWork(@Param("announcementCode") int announcementCode, @Param("workOptions") List<String> options );

	public void insertMulAca(@Param("announcementCode") int announcementCode, @Param("acaOptions") List<String> options );

	public List<JobItem> selectAll();

	public JobItem selectByAnnCode(int listAnnCode);
	
//	public JobItem selectByCorporationNo(int corporationNo);
}
