package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.ApplicantInfo;
import sample.project.jobissue.domain.ApplicantManage;
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
	public void insertPreMulEmp(int announcementCode, List<String> options) {
		// TODO Auto-generated method stub
		Integer result = prMapper.insertPreMulEmp(announcementCode, options);
		log.info("insertMulOp {}" , result);
	}

	@Override
	public void insertPreMulWork(int announcementCode, List<String> options) {
		// TODO Auto-generated method stub
		Integer result = prMapper.insertPreMulWork(announcementCode, options);
		log.info("insert MulWork {}", result);
	}

	@Override
	public void insertPreMulAca(int announcementCode, List<String> options) {
		// TODO Auto-generated method stub
		Integer result = prMapper.insertPreMulAca(announcementCode, options);
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
	public PreRecruitment selectByPreAnnCode(int listAnnCode) {
		// TODO Auto-generated method stub
		PreRecruitment preRecruitment = prMapper.selectByPreAnnCode(listAnnCode);
		log.info("selectByAnnCode {}", listAnnCode);
		
		return preRecruitment;
	}

	@Override
	public boolean update(int announcementCode, PreRecruitment preRecruitment) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			prMapper.update(announcementCode, preRecruitment);
			result = true;
			
			prMapper.deletePreMulAca(announcementCode);
			prMapper.deletePreMulEmp(announcementCode);
			prMapper.deletePreMulWork(announcementCode);
			
			prMapper.insertPreMulAca(
					preRecruitment.getAnnouncementCode(), preRecruitment.getAcademicRecordCode());
			prMapper.insertPreMulEmp(
					preRecruitment.getAnnouncementCode(), preRecruitment.getEmployTypeCode());
			prMapper.insertPreMulWork(
					preRecruitment.getAnnouncementCode(), preRecruitment.getWorkingAreaCode());
		
			log.info("update info {}", preRecruitment);
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			log.error("preRecruitment update error {} {}"
					, announcementCode, preRecruitment);
		}
		return result;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		prMapper.deleteAll();
	}
	
	

	@Override
	public List<PreRecruitment> selectByPreCorCode(int corCode) {
		// TODO Auto-generated method stub
		List<PreRecruitment> preRecruitment = prMapper.selectByPreCorCode(corCode);
		log.info("selectByCorCode {}", corCode);
		
		return preRecruitment;
	}

	@Override
	public void deleteByAnnouncementCode(int announcementCode) {
		// TODO Auto-generated method stub
//		PreRecruitment preRecruitment = prMapper.selectByPreAnnCode(announcementCode);
		prMapper.deleteByAnnouncementCode(announcementCode);
		log.info("deleteByAnnoncement {}", announcementCode);
		
		
	}

	@Override
	public List<ApplicantManage> selectByCorCode(int corCode) {
		// TODO Auto-generated method stub
		List<ApplicantManage> applicant = prMapper.selectByCorCode(corCode);
		log.info("selectByCorCode {}", corCode);
		
		return applicant;
	}

	@Override
	public ApplicantInfo userSelectByAnnCode(int announcementCode) {
		// TODO Auto-generated method stub
		ApplicantInfo applicant = prMapper.userSelectByAnnCode(announcementCode);
		log.info("selectByAnnCode {}", announcementCode);
		
		return applicant;
	}

	@Override
	public List<ApplicantInfo> selectByAnnSubmit(int announcementCode) {
		// TODO Auto-generated method stub
		List<ApplicantInfo> applicant = prMapper.selectByAnnSubmit(announcementCode);
		log.info("selectBySubmit {}", announcementCode);
		return applicant;
	}
//
//	@Override
//	public void deletePreMulEmp(int announcementCode) {
//		// TODO Auto-generated method stub
//		PreRecruitment preRecruitment = prMapper.selectByAnnCode(announcementCode);
//		log.info("deletePreMulEmp {}", announcementCode);
//	}
//
//	@Override
//	public void deletePreMulAca(int announcementCode) {
//		// TODO Auto-generated method stub
//		PreRecruitment preRecruitment = prMapper.selectByAnnCode(announcementCode);
//		log.info("deletePreMulAca {}", announcementCode);
//	}
//
//	@Override
//	public void deletePreMulWork(int announcementCode) {
//		// TODO Auto-generated method stub
//		PreRecruitment preRecruitment = prMapper.selectByAnnCode(announcementCode);
//		log.info("deletePreMulWork {}", announcementCode);
//	}


}
