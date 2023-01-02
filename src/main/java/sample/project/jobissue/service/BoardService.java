package sample.project.jobissue.service;

import java.util.List;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.SearchItem;

public interface BoardService {
	public List<JobItem> findHomeByName(SearchItem searchItem);
}
