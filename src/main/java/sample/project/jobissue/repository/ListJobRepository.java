package sample.project.jobissue.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import sample.project.jobissue.domain.JobItem;


@Repository
public class ListJobRepository {
	private static List<JobItem> db = new ArrayList<>();

	private static int seq = 1;

	
	public JobItem insert(JobItem jobItem) {
		jobItem.setCorporationNo(seq++);
		db.add(jobItem);
		return jobItem;
	}
	
	public List<JobItem> selectAll() {
		return db;
	}
	
	public JobItem selectByCorporationNo(int corporationNo) {
		for(JobItem jobItem : db) {
			if(jobItem.getCorporationNo() == corporationNo) {
				return jobItem;
			}
		}
		return null;
	}
}
