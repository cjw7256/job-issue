package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.repository.JobRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MybatisJobRepository implements JobRepository{
	
	private final JobMapper jobMapper;
	
	@Override
	@Transactional
	public JobItem insert(JobItem jobItem) {
		// TODO Auto-generated method stub
		Integer result = jobMapper.insert(jobItem);
		log.info("FoodItem insert result {}", result);
				
		return jobItem;
	}
	
	@Override
	public List<JobItem> selectAll() {
		// TODO Auto-generated method stub
		List<JobItem> jobItems = null;
		try {
			jobItems = jobMapper.selectAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jobItems;
	}
	
	@Override
	public JobItem selectByCorporationNo(int corporationNo) {
		// TODO Auto-generated method stub
		JobItem jobItem = jobMapper.selectByCorporationNo(corporationNo);
		log.info("jobItem {}", jobItem);

		return jobItem;
	}
	
	
}
