package sample.project.jobissue.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import sample.project.jobissue.domain.ResumeItem;
import sample.project.jobissue.domain.UserVO;

@Mapper
public interface ResumeMapper {
	
	public Integer insertResume(ResumeItem resumeItem);
	
	public ResumeItem selectByUserCode(int userCode);
	
	public void update(@Param("userCode") int userCode, @Param("updateItem") ResumeItem resumeItem);
	
	public void deleteResume(@Param("userCode") int userCode, @Param("deleteItem") ResumeItem resumeItem);
	
	public void insertAfter(@Param("userCode") int userCode, @Param("updateItem") UserVO userVO);
	
	public void deleteAfter(@Param("userCode") int userCode, @Param("updateItem") UserVO userVO);
	
	
}
