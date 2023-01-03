package sample.project.jobissue.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.PreRecruitment;

public interface PreRecruitmentRepository {

	public PreRecruitment insertPreRecruit(PreRecruitment preRecruitment);
	
	public PreRecruitment selectById(int id);
	
	public void insertCorInfo(String corName);
	
	public void insertPreMulEmp(@Param("announcementCode") int announcementCode
			, @Param("empOptions") List<String> options );

	public void insertPreMulWork(@Param("announcementCode") int announcementCode
			, @Param("workOptions") List<String> options );

	public void insertPreMulAca(@Param("announcementCode") int announcementCode
			, @Param("acaOptions") List<String> options );
	
	public List<PreRecruitment> selectAll();

	public PreRecruitment selectByPreAnnCode(int listAnnCode);
	
	public List<PreRecruitment> selectByPreCorCode(int corCode);
	
	//공고 삭제
	public void deleteByAnnouncementCode(int announcementCode);
	
	
	public void deleteAll();

	public boolean update(@Param("announcementCode")int announcementCode, @Param("updateItem")PreRecruitment preRecruitment);
}
