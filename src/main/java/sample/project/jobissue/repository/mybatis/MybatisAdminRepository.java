package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.PreRecruitment;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.repository.AdminRepository;
import sample.project.jobissue.repository.JobRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MybatisAdminRepository implements AdminRepository{
	
	private final AdminMapper adminMapper;

	@Override
	public List<UserVO> selectCorUserInfoList() {
		// TODO Auto-generated method stub
		List<UserVO> corUserList = null;
		try {
			corUserList = adminMapper.selectCorUserInfoList();
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return corUserList;
	}

	@Override
	public List<UserVO> selectUserInfoList() {
		// TODO Auto-generated method stub
		List<UserVO> userList = null;
		try {
			userList = adminMapper.selectUserInfoList();
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return userList;
	}

	@Override
	public List<PreRecruitment> selectPreAll() {
		// TODO Auto-generated method stub
		List<PreRecruitment> preList = null;
		
		try {
			preList = adminMapper.selectPreAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return preList;
	}

	@Override
	public PreRecruitment selectPreByAnnCode(int annCode) {
		// TODO Auto-generated method stub
		PreRecruitment prc = adminMapper.selectPreByAnnCode(annCode);
		
		return prc;
	}

	@Override
	public List<String> selectPreEmployType(int annCode) {
		// TODO Auto-generated method stub
		List<String> employList = null;
		
		try {
			employList = adminMapper.selectPreEmployType(annCode);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return employList;
	}

	@Override
	public List<String> selectPreWorkingArea(int annCode) {
		// TODO Auto-generated method stub
		List<String> waList = null;
		
		try {
			waList = adminMapper.selectPreWorkingArea(annCode);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return waList;
	}

	@Override
	public List<String> selectPreAcademicRecord(int annCode) {
		// TODO Auto-generated method stub
		List<String> acaList = null;
		
		try {
			acaList = adminMapper.selectPreAcademicRecord(annCode);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return acaList;
	}

	//insert문 시작
	
	@Override
	public PreRecruitment insertPreToRecru(PreRecruitment preRecruitment) {
		// TODO Auto-generated method stub
		Integer result = adminMapper.insertPreToRecru(preRecruitment); 
		
		return null;
	}

	@Override
	public void insertPreToMulEmp(int announcementCode, List<String> options) {
		// TODO Auto-generated method stub
		Integer result = adminMapper.insertPreToMulEmp(announcementCode, options);
	}

	@Override
	public void insertPreToMulWork(int announcementCode, List<String> options) {
		// TODO Auto-generated method stub
		Integer result = adminMapper.insertPreToMulWork(announcementCode, options);
	}

	@Override
	public void insertPreToMulAca(int announcementCode, List<String> options) {
		// TODO Auto-generated method stub
		Integer result = adminMapper.insertPreToMulAca(announcementCode, options);
	}
	
	//update
	
	@Override
	@Transactional //만약 option delete+insert 과정이 잘 이루어지지 않았을 경우, 아예 모든 과정이 실행되지 않도록
	public boolean updatePreStat(String applyStat, int annCode) {
		// TODO Auto-generated method stub
		log.info("before updatePreStat{}, {}", applyStat, annCode);
		
		boolean result = false;
		
		try {
			adminMapper.updatePreStat(applyStat, annCode);
			
			result = true;
			
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return result;
	}

	//변경부분~

	@Override
	public void deleteRecByAdmin(int annCode) {
		// TODO Auto-generated method stub
		log.info("deleteRecByAdmin 실행 - 관리자가 공고 삭제");
		
		adminMapper.deleteRecByAdmin(annCode);
	}

	@Transactional
	@Override
	public Integer insertRecruToDel(JobItem jobItem) {
		// TODO Auto-generated method stub
		Integer result = adminMapper.insertRecruToDel(jobItem); //delRec에 저장
		//그리고 옵션들도 같이 저장됨!
		Integer option1 = adminMapper.insertRecToDelEmp(jobItem.getAnnouncementCode(), jobItem.getEmployTypeCode());
		Integer option2 = adminMapper.insertRecToDelWork(jobItem.getAnnouncementCode(), jobItem.getWorkingAreaCode());
		Integer option3 = adminMapper.insertRecToDelAca(jobItem.getAnnouncementCode(), jobItem.getAcademicRecordCode());
		
		log.info("insertRecruToDel 실행 완료 {}, {}, {}, {}", result, option1, option2, option3);
		
		return result;
	}

	@Override
	public UserVO selectUserDetailInfo(int userCode) {
		// TODO Auto-generated method stub
		UserVO userDetailInfo = adminMapper.selectUserDetailInfo(userCode);
		
		return userDetailInfo;
	}

	@Transactional
	@Override
	public void deleteUserByAdmin(int userCode, String resumeCode) {
		// TODO Auto-generated method stub
		//userInfo에서 정보 지우고
		adminMapper.deleteUserByAdmin(userCode);
		//이력서 테이블에서도...!! - 이력서 작성 여부가 긍정일경우
		if(resumeCode.equals("Y")) {
			adminMapper.deleteResumeByAdmin(userCode);
		}
		log.info("delete User 완료");
	}

	@Override
	public UserVO selectCorDetailInfo(int userCode) {
		// TODO Auto-generated method stub
		UserVO corInfo = adminMapper.selectCorDetailInfo(userCode);
		
		return corInfo;
	}
	
}
