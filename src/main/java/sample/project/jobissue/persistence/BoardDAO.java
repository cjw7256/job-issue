package sample.project.jobissue.persistence;

import java.util.List;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.SearchItem;

public interface BoardDAO {
	public List<JobItem> selectHomeByName(SearchItem searchItem);
}
