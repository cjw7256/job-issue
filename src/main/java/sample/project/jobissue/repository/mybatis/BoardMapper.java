package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.SearchItem;

@Mapper
public interface BoardMapper {
	public List<JobItem> selectHomeByName(SearchItem searchItem);
}
