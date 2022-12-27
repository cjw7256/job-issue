package sample.project.jobissue.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.PreRecruitment;

public interface PreRecruitmentRepository {

	public PreRecruitment insertPreRecruit(PreRecruitment preRecruitment);
	
	public PreRecruitment selectById(int id);
	
	public void insertCorInfo(String corName);
	
	public void insertMulEmp(@Param("announcementCode") int announcementCode
			, @Param("empOptions") List<String> options );

	public void insertMulWork(@Param("announcementCode") int announcementCode
			, @Param("workOptions") List<String> options );

	public void insertMulAca(@Param("announcementCode") int announcementCode
			, @Param("acaOptions") List<String> options );
	
	public List<PreRecruitment> selectAll();

	public PreRecruitment selectByAnnCode(int listAnnCode);
	
//	public boolean update(int announcementCode , PreRecruitment preRecruitment);
	
	public void deleteAll();
}
