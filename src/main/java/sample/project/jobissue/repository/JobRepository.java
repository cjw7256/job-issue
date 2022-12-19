package sample.project.jobissue.repository;

import java.util.List;

import sample.project.jobissue.domain.JobItem;

public interface JobRepository {

	public JobItem insert(JobItem jobItem);

	public List<JobItem> selectAll();

	public JobItem selectByCorporationNo(int corporationNo);
}
