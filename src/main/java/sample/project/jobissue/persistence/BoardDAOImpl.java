package sample.project.jobissue.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.SearchItem;
import sample.project.jobissue.repository.mybatis.BoardMapper;

@Repository
@RequiredArgsConstructor
public class BoardDAOImpl implements BoardDAO{

	private final BoardMapper boardMapper;
	
	@Override
	public List<JobItem> selectHomeByName(SearchItem searchItem) {
		// TODO Auto-generated method stub
		List<JobItem> jobItemList = boardMapper.selectHomeByName(searchItem);
		
		return jobItemList;
	}

}
