package sample.project.jobissue.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.PreRecruitment;
import sample.project.jobissue.domain.RejReasonInfo;
import sample.project.jobissue.repository.AdminRepository;
import sample.project.jobissue.repository.JobRepository;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminRepository adminRepository;
	private final JobRepository jobRepository;
	
	/**
	 * 공고를 승인하는 메서드
	 * @param annCode
	 * @return none
	 * @author 윤서연
	 */
	public void applyRec(int annCode) {
		//공고 번호로 승인 대기 상태의 공고 찾기
		PreRecruitment preRec = adminRepository.selectPreByAnnCode(annCode); 
		log.info("applyAnnPostPage pre {}", preRec);
		
		saveRec(annCode, preRec);
		adminRepository.deletePreRecByAdmin(annCode); //공고 승인 시, 승인된 공고를 rec로 옮기고 승인 대기 테이블에서는 삭제함
//		changeApplyCode(annCode, preRec);
	}

	//승인 대기 공고 저장하기
	@Transactional
	private void saveRec(int annCode, PreRecruitment preRec) {
		adminRepository.insertPreToRecru(preRec);
		
		adminRepository.insertPreToMulEmp(annCode, preRec.getEmployTypeCode());
		adminRepository.insertPreToMulWork(annCode, preRec.getWorkingAreaCode());
		adminRepository.insertPreToMulAca(annCode, preRec.getAcademicRecordCode());
		log.info("applyAnnPostPage 임시 저장 완료");
	}
	
	//승인 대기 공고의 승인코드 변경하기~
	private void changeApplyCode(int annCode, PreRecruitment preRec) {
		preRec.setApplyStat("01"); //승인코드인 01로 변경
		adminRepository.updatePreStat(preRec.getApplyStat(), annCode);
		log.info("applyAnnPostPage 승인코드 변경 완료");
	}


	
	/** 공고 거절 처리 메소드
	 * @param annCode
	 * @param preRec
	 */
	public void rejectRec(RejReasonInfo rejInfo) {
		int annCode = rejInfo.getAnnouncementCode();
		
		//공고 번호로 임시 저장된 공고 찾기
		PreRecruitment preRec = adminRepository.selectPreByAnnCode(annCode); 
		
		changerejectCode(annCode, preRec); //승인코드 변경하기
		
		int result =  adminRepository.insRejReasonInfo(rejInfo); //거절 사유 저장하는 메소드!
		log.info("rejectRec {}", result);
	}

//	승인 대기 공고의 승인코드 - 거절로 변경하기~
	private void changerejectCode(int annCode, PreRecruitment preRec) {
		preRec.setApplyStat("02"); //거절코드인 02로 변경

		adminRepository.updatePreStat(preRec.getApplyStat(), annCode); //거절코드 02로 변경시키는 메소드
		log.info("applyAnnPostPage 승인 대기 공고 -> 거절");
	}
	
	
	/** 공고를 delRec테이블에 저장 후 recruitment 테이블에서 삭제함
	 * @param annCode
	 */
	public void deleteRec(int annCode) {
		insertRecToDel(annCode);
		adminRepository.deleteRecByAdmin(annCode);
		
		log.info("공고 삭제 처리 완료");
	}
	
	//삭제될 공고를 delRec테이블에 저장함
	private void insertRecToDel(int annCode) {
		JobItem jobItem = jobRepository.selectByAnnCode(annCode);

		adminRepository.insertRecruToDel(jobItem);
	}
	
	
	/** 기업 회원 정보를 삭제 처리하는 메소드
	 * @param userCode
	 * @param corCode
	 */
	public void deleteCorDetail(int userCode, int corCode) {
		adminRepository.deleteCorUserByAdmin(userCode, corCode);

		deleteRecAll(corCode);
		deletePreRecAll(corCode);
		
		log.info("기업 회원정보 삭제 처리 완료");
	}

	//기업의 모든 공고 정보를 삭제함
	private void deleteRecAll(int corCode) {
		List<Integer> annCodes =  adminRepository.selectRecCodes(corCode);
		
		if(!annCodes.isEmpty() & annCodes != null) {
			for(int annCode : annCodes) {
				adminRepository.deleteRecByAdmin(annCode); //돌면서 annCode를 넣어주면서 삭제할 수 있게!
			}
		}
	}
	
	//기업의 모든 승인대기 공고 정보를 삭제
	private void deletePreRecAll(int corCode) {
		//pre~ 삭제
		List<Integer> preAnnCodes = adminRepository.selectPreRecCodes(corCode);
		
		if(!preAnnCodes.isEmpty() & preAnnCodes != null) {
			for(int preAnnCode : preAnnCodes) {
				adminRepository.deletePreRecByAdmin(preAnnCode); //돌면서 annCode를 넣어주면서 삭제할 수 있게!
			}
		}
	}
}
