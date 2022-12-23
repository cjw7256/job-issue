package sample.project.jobissue.repository.mybatis;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.ResumeItem;
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

}