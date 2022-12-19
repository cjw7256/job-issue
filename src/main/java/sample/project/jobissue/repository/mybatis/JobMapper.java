package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import sample.project.jobissue.domain.JobItem;

@Mapper
public interface JobMapper {
	
	public Integer insert(JobItem jobItem);
	
	public List<JobItem> selectAll();
	
	public JobItem selectByCorporationNo(int corporationNo);
}
