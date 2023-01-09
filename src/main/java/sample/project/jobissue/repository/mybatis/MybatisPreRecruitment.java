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
	
	//공고 등록 -> 승인 대기 공고
	@Override
	@Transactional
	public PreRecruitment insertPreRecruit(PreRecruitment preRecruitment) {
		// TODO Auto-generated method stub
		Integer result = prMapper.insertPreRecruit(preRecruitment);
		log.info("preRecruitment insert result {}", result);
		
		return preRecruitment;
	}

	//중복 가능한 항목 삽입
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


	
	//임시 공고 수정
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

	//임시 공고 목록 출력
	@Override
	public List<PreRecruitment> selectByPreCorCode(int corCode) {
		// TODO Auto-generated method stub
		List<PreRecruitment> preRecruitment = prMapper.selectByPreCorCode(corCode);
		log.info("selectByCorCode {}", corCode);
		
		return preRecruitment;
	}
	
	//승인된 공고 목록 출력
	@Override
	public List<ApplicantManage> selectByCorCode(int corCode) {
		// TODO Auto-generated method stub
		List<ApplicantManage> applicant = prMapper.selectByCorCode(corCode);
		log.info("selectByCorCode {}", corCode);
		
		return applicant;
	}
	
	//임시 공고 상세 출력
	@Override
	public PreRecruitment selectByPreAnnCode(int annCode) {
		// TODO Auto-generated method stub
		PreRecruitment preRecruitment = prMapper.selectByPreAnnCode(annCode);
		log.info("selectByAnnCode {}", annCode);
		
		return preRecruitment;
	}
	
	//승인된 공고 상세 표기
	@Override
	public PreRecruitment selectByAnnCode(int annCode) {
		// TODO Auto-generated method stub
		PreRecruitment preRecruitment = prMapper.selectByAnnCode(annCode);
		log.info("selectByAnnCode {}", annCode);
		
		return preRecruitment;
	}
	
	//공고 삭제
	@Override
	public void deleteByAnnouncementCode(int announcementCode) {
		// TODO Auto-generated method stub
//		PreRecruitment preRecruitment = prMapper.selectByPreAnnCode(announcementCode);
		prMapper.deleteByAnnouncementCode(announcementCode);
		log.info("deleteByAnnoncement {}", announcementCode);
		
		
	}

	

	//제출된 이력서 상세 출력
	@Override
	public ApplicantInfo userSelectByAnnCode(int announcementCode) {
		// TODO Auto-generated method stub
		ApplicantInfo applicant = prMapper.userSelectByAnnCode(announcementCode);
		log.info("selectByAnnCode {}", announcementCode);
		
		return applicant;
	}

	//지원자 정보 목록
	@Override
	public List<ApplicantInfo> selectByAnnSubmit(int announcementCode) {
		// TODO Auto-generated method stub
		List<ApplicantInfo> applicant = prMapper.selectByAnnSubmit(announcementCode);
		log.info("selectBySubmit {}", announcementCode);
		return applicant;
	}





}
