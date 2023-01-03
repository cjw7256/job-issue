package sample.project.jobissue.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.SearchItem;
import sample.project.jobissue.persistence.BoardDAO;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardDAO boardDAO; 
	
	@Override
	public List<JobItem> findHomeByName(SearchItem searchItem) {
		// TODO Auto-generated method stub
		List<JobItem> jobItemList = boardDAO.selectHomeByName(searchItem);
		
		return jobItemList;
	}

}
