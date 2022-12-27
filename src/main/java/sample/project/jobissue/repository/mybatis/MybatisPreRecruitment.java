package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.PreRecruitment;
import sample.project.jobissue.repository.PreRecruitmentRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MybatisPreRecruitment implements PreRecruitmentRepository{

	private final PreRecruitmentMapper prMapper;
	
	
	@Override
	@Transactional
	public PreRecruitment insertPreRecruit(PreRecruitment preRecruitment) {
		// TODO Auto-generated method stub
		Integer result = prMapper.insertPreRecruit(preRecruitment);
		log.info("preRecruitment insert result {}", result);
		
		return preRecruitment;
	}

	@Override
	public PreRecruitment selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertCorInfo(String corName) {
		// TODO Auto-generated method stub
		Integer result = prMapper.insertCorInfo(corName);
		log.info("insert Cor {}", result);
	}

	@Override
	public void insertMulEmp(int announcementCode, List<String> options) {
		// TODO Auto-generated method stub
		Integer result = prMapper.insertMulEmp(announcementCode, options);
		log.info("insertMulOp {}" , result);
	}

	@Override
	public void insertMulWork(int announcementCode, List<String> options) {
		// TODO Auto-generated method stub
		Integer result = prMapper.insertMulWork(announcementCode, options);
		log.info("insert MulWork {}", result);
	}

	@Override
	public void insertMulAca(int announcementCode, List<String> options) {
		// TODO Auto-generated method stub
		Integer result = prMapper.insertMulAca(announcementCode, options);
		log.info("insert MulAca {} " , result);
	}

	@Override
	public List<PreRecruitment> selectAll() {
		// TODO Auto-generated method stub
		List<PreRecruitment> preRecruitment = null;
		try {
			preRecruitment = prMapper.selectAll();
		} catch(Exception e) {
			log.error(e.getMessage());
		}
		return preRecruitment;
	}

	@Override
	public PreRecruitment selectByAnnCode(int listAnnCode) {
		// TODO Auto-generated method stub
		PreRecruitment preRecruitment = prMapper.selectByAnnCode(listAnnCode);
		log.info("selectByAnnCode {}", listAnnCode);
		
		return preRecruitment;
	}

//	@Override
//	public boolean update(int announcementCode, PreRecruitment preRecruitment) {
//		// TODO Auto-generated method stub
//		boolean result = false;
//		try {
//			prMapper.update(announcementCode, preRecruitment);
//			result = true;
//			
//			prMapper.
//		}
//		return false;
//	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		prMapper.deleteAll();
	}


}
