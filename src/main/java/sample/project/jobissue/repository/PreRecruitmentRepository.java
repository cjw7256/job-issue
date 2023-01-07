package sample.project.jobissue.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.ApplicantInfo;
import sample.project.jobissue.domain.ApplicantManage;
import sample.project.jobissue.domain.PreRecruitment;

public interface PreRecruitmentRepository {

	//공고 등록
	public PreRecruitment insertPreRecruit(PreRecruitment preRecruitment);
			
	public void insertPreMulEmp(@Param("announcementCode") int announcementCode
			, @Param("empOptions") List<String> options );

	public void insertPreMulWork(@Param("announcementCode") int announcementCode
			, @Param("workOptions") List<String> options );

	public void insertPreMulAca(@Param("announcementCode") int announcementCode
			, @Param("acaOptions") List<String> options );
	
	//임시 공고 상세 출력
	public PreRecruitment selectByPreAnnCode(int listAnnCode);
	
	//승인된 공고 상세 출력
	public PreRecruitment selectByAnnCode(int listAnnCode);

	//임시 공고 목록 출력
	public List<PreRecruitment> selectByPreCorCode(int corCode);
	
	//승인된 공고 목록 출력
	public List<ApplicantManage> selectByCorCode(int corCode);

	//공고 삭제
	public void deleteByAnnouncementCode(int announcementCode);
	
	//공고 업데이트
	public boolean update(@Param("announcementCode")int announcementCode, @Param("updateItem")PreRecruitment preRecruitment);

	
	//지원자 이력서 상세 출력
	public ApplicantInfo userSelectByAnnCode(int announcementCode);
	
	//지원자 목록 출력
	public List<ApplicantInfo> selectByAnnSubmit(int announcementCode);


}
