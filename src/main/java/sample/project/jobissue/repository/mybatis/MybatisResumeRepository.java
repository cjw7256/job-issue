package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.ResumeItem;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.repository.ResumeRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MybatisResumeRepository implements ResumeRepository {
	
	private final ResumeMapper resumeMapper;
	
	@Override
	@Transactional
	public ResumeItem insertResume(ResumeItem resumeItem) {
		// TODO Auto-generated method stub
		resumeMapper.insertResume(resumeItem);
		return resumeItem;
	}

	@Override
	public boolean insertAfter(int userCode, UserVO userVO) {
		// TODO Auto-generated method stub
		boolean result = false;
		
		try {
			resumeMapper.insertAfter(userCode, userVO);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("{}", e.getMessage());
		}
		return result;
	}
	
	@Override
	public boolean deleteAfter(int userCode, UserVO userVO) {
		// TODO Auto-generated method stub
		boolean result = false;
		
		try {
			resumeMapper.deleteAfter(userCode, userVO);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("{}", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResumeItem selectByUserCode(int userCode) {
		// TODO Auto-generated method stub
		ResumeItem resumeItem = resumeMapper.selectByUserCode(userCode);
		log.info("resumeItem {}",resumeItem);
		return resumeItem;
	}
	
	
	@Override
	@Transactional
	public boolean update(int userCode, ResumeItem resumeItem) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			resumeMapper.update(userCode, resumeItem);
			result = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("{}", e.getMessage());
			log.error("resumeMapper update error {} {} ", userCode, resumeItem);
		}
		return result;
	}

	@Override
	@Transactional
	public boolean deleteResume(int userCode, ResumeItem resumeItem) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			resumeMapper.deleteResume(userCode, resumeItem);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("{}", e.getMessage());
		}
		return result;
	}

	@Override
	public List<JobItem> selectBySubmit(int usercCode) {
		// TODO Auto-generated method stub
		List<JobItem> jobItems = null;
		try {
			jobItems = resumeMapper.selectBySubmit(usercCode);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jobItems;
	}
	
	@Override
	public JobItem selectByAnnCode(int submitListAnnCode) {
		// TODO Auto-generated method stub
		JobItem jobItem = resumeMapper.selectByAnnCode(submitListAnnCode);
		log.info("selectByAnnCode {}", submitListAnnCode);

		return jobItem;
	}

	

	public void insertMulEmp(int announcementCode, List<String> employTypeCode) {
		Integer result = resumeMapper.insertMulEmp(announcementCode, employTypeCode);
		log.info("insertMulOp {}", result);
	}
	
	public void insertMulWork(int announcementCode, List<String> workAreaCode ) {
		Integer result = resumeMapper.insertMulWork(announcementCode, workAreaCode);
		log.info("insertMulWork {}", result);
	};

	public void insertMulAca(int announcementCode, List<String> acaCode ){
		Integer result = resumeMapper.insertMulAca(announcementCode, acaCode);
		log.info("insertMulAca {}", result);
	}

	@Override
	@Transactional
	public boolean deleteSubmitResume(int userCode, int submitListAnnCode) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			resumeMapper.deleteSubmitResume(userCode, submitListAnnCode);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("{}", e.getMessage());
		}
		return result;
	}
	
	
}
