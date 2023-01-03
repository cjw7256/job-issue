package sample.project.jobissue.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.ResumeItem;

@Mapper
public interface JobApplicationMapper {
	public ResumeItem selectByUserResume(int userCode);
	
	
	public Integer insertSubmitResume
	(@Param("corCode") int corCode,
	 @Param("announcementCode") int announcementCode, 
	 @Param("userCode") int userCode);
	
}


