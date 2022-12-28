package sample.project.jobissue.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;

import sample.project.jobissue.domain.ResumeItem;

@Mapper
public interface JobApplicationMapper {
	public ResumeItem selectByUserResume(int userCode);
}
