package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.Pagination;
import sample.project.jobissue.domain.UserVO;
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
	public JobItem insertRecruitInit(JobItem jobItem) {
		// TODO Auto-generated method stub
		Integer result = jobMapper.insertRecruitInit(jobItem);
		log.info("jobItem insert result {}", result);
				
		return jobItem;
	}

	public void insertCorInfo(String corName) {
		Integer result = jobMapper.insertCorInfo(corName);
		log.info("insertCor {}", result);
	}
	
	@Override
	public UserVO insertCorInitInfo(UserVO user) {
		// TODO Auto-generated method stub
		Integer result = jobMapper.insertCorInitInfo(user);
		log.info("corinit insert {}", result);
		
		return user;
	}

	@Override
	public void insertUserInfoAsCor(UserVO user) {
		// TODO Auto-generated method stub
		Integer result = jobMapper.insertUserInfoAsCor(user);
		log.info("insertUserInfoAsCor {}", result );
	}
	
	public void insertMulEmp(int announcementCode, List<String> employTypeCode) {
		Integer result = jobMapper.insertMulEmp(announcementCode, employTypeCode);
		log.info("insertMulOp {}", result);
	}
	
	public void insertMulWork(int announcementCode, List<String> workAreaCode ) {
		Integer result = jobMapper.insertMulWork(announcementCode, workAreaCode);
		log.info("insertMulWork {}", result);
	};

	public void insertMulAca(int announcementCode, List<String> acaCode ){
		Integer result = jobMapper.insertMulAca(announcementCode, acaCode);
		log.info("insertMulAca {}", result);
	};

	
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
	public JobItem selectByAnnCode(int listAnnCode) {
		// TODO Auto-generated method stub
		JobItem jobItem = jobMapper.selectByAnnCode(listAnnCode);
		log.info("selectByAnnCode {}", listAnnCode);
		log.info("select complete {}", jobItem);

		return jobItem;
	}

	@Override
	public Integer selectTotalCnt() {
		// TODO Auto-generated method stub
		Integer result = jobMapper.selectTotalCnt();
		log.info("selectTotalCnt {}", result);
		
		return result;
	}

	@Override
	public List<JobItem> selJobListPagingList(Pagination pagination) {
		// TODO Auto-generated method stub
		List<JobItem> jobItemList = jobMapper.selJobListPagingList(pagination);
		
		log.info("selJobListPaging");
		
		return jobItemList;
	}
	
}
