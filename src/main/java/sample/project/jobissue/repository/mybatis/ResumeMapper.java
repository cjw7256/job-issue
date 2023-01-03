package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.ResumeItem;
import sample.project.jobissue.domain.UserVO;

@Mapper
public interface ResumeMapper {
	
	public Integer insertResume(ResumeItem resumeItem);
	
	public ResumeItem selectByUserCode(int userCode);
	
	public List<JobItem> selectBySubmit(int userCode);
		
	public Integer insertMulEmp(@Param("announcementCode") int announcementCode, @Param("empOptions") List<String> options );

	public Integer insertMulWork(@Param("announcementCode") int announcementCode, @Param("workOptions") List<String> options );

	public Integer insertMulAca(@Param("announcementCode") int announcementCode, @Param("acaOptions") List<String> options );
	
	
	public List<String> selectEmployType(int submitListAnnCode);
	
	public List<String> selectWorkingArea(int submitListAnnCode);
	
	public List<String> selectAcademicRecord(int submitListAnnCode);
	
	public JobItem selectByAnnCode(int submitListAnnCode);
	
	public void update(@Param("userCode") int userCode, @Param("updateItem") ResumeItem resumeItem);
	
	public void deleteResume(@Param("userCode") int userCode, @Param("deleteItem") ResumeItem resumeItem);
	
	public void insertAfter(@Param("userCode") int userCode, @Param("updateItem") UserVO userVO);
	
	public void deleteAfter(@Param("userCode") int userCode, @Param("updateItem") UserVO userVO);
	
	public void deleteSubmitResume(@Param("userCode") int userCode, @Param("submitListAnnCode")int submitListAnnCode);
	
}
