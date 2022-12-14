package sample.project.jobissue.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.Pagination;
import sample.project.jobissue.domain.PreRecruitment;
import sample.project.jobissue.domain.RejReasonInfo;
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

	//insert??? ??????
	
	@Override
	public PreRecruitment insertPreToRecru(PreRecruitment preRecruitment) {
		// TODO Auto-generated method stub
		Integer result = adminMapper.insertPreToRecru(preRecruitment); 
		
		return preRecruitment;
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
	@Transactional //?????? option delete+insert ????????? ??? ??????????????? ????????? ??????, ?????? ?????? ????????? ???????????? ?????????
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

	//????????????~

	@Override
	public void deleteRecByAdmin(int annCode) {
		// TODO Auto-generated method stub
		log.info("deleteRecByAdmin ?????? - ???????????? ?????? ??????");
		
		adminMapper.deleteRecByAdmin(annCode); //?????? ??????
		
		adminMapper.deleteEmpOptByAdmin(annCode); //?????? ?????? ??????
		adminMapper.deleteWorkOptByAdmin(annCode);
		adminMapper.deleteAcaOptByAdmin(annCode);
	}

	@Transactional
	@Override
	public Integer insertRecruToDel(JobItem jobItem) {
		// TODO Auto-generated method stub
		Integer result = adminMapper.insertRecruToDel(jobItem); //delRec??? ??????
		//????????? ???????????? ?????? ?????????!
		Integer option1 = adminMapper.insertRecToDelEmp(jobItem.getAnnouncementCode(), jobItem.getEmployTypeCode());
		Integer option2 = adminMapper.insertRecToDelWork(jobItem.getAnnouncementCode(), jobItem.getWorkingAreaCode());
		Integer option3 = adminMapper.insertRecToDelAca(jobItem.getAnnouncementCode(), jobItem.getAcademicRecordCode());
		
		log.info("insertRecruToDel ?????? ?????? {}, {}, {}, {}", result, option1, option2, option3);
		
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
		//userInfo?????? ?????? ?????????
		adminMapper.deleteUserByAdmin(userCode);
		//????????? ??????????????????...!! - ????????? ?????? ????????? ???????????????
		if(resumeCode.equals("Y")) {
			adminMapper.deleteResumeByAdmin(userCode);
		}
		log.info("delete User ??????");
	}
	
	@Override
	public UserVO selectCorDetailInfo(int userCode) {
		// TODO Auto-generated method stub
		UserVO corInfo = adminMapper.selectCorDetailInfo(userCode);
		
		return corInfo;
	}
	
	@Override
	public void deleteCorUserByAdmin(int userCode, int corCode) {
		// TODO Auto-generated method stub
		//????????? ?????? -> corporation info , user_info, recruitment, pre~ ?????? ???????????? ???
		adminMapper.deleteUserByAdmin(userCode); //user_info?????? ?????? ??????
		adminMapper.deleteCorInfoByAdmin(corCode); //cor_info?????? ?????? ??????
	}

	@Override
	public List<Integer> selectRecCodes(int corCode) {
		// TODO Auto-generated method stub
		List<Integer> annCodes = adminMapper.selectRecCodes(corCode);
		
		return annCodes;
	}

	@Override
	public List<Integer> selectPreRecCodes(int corCode) {
		// TODO Auto-generated method stub
		List<Integer> annCodes = adminMapper.selectPreRecCodes(corCode);
		
		return annCodes;
	}

	@Override
	public void deletePreRecByAdmin(int annCode) {
		// TODO Auto-generated method stub
		adminMapper.deletePreRecByAdmin(annCode);
		
		adminMapper.deletePreEmpOptByAdmin(annCode);
		adminMapper.deletePreAcaOptByAdmin(annCode);
		adminMapper.deletePreWorkOptByAdmin(annCode);
		
		log.info("?????? ?????? ????????? ????????? ?????? ??????");		
	}

	@Override
	public void deleteResumeByDrop(int userCode) {
		// TODO Auto-generated method stub
		adminMapper.deleteResumeByAdmin(userCode);
		
		log.info("deleteResumeByDrop :: ?????? ????????? ?????? ????????? ?????? ?????? ??????");
	}

	@Override
	public List<UserVO> selCorForMain() {
		// TODO Auto-generated method stub
		List<UserVO> corList = adminMapper.selCorForMain();
		return corList;
	}

	@Override
	public List<UserVO> selUserForMain() {
		// TODO Auto-generated method stub
		List<UserVO> userList = adminMapper.selUserForMain();
		return userList;
	}

	@Override
	public List<PreRecruitment> selPreForMain() {
		// TODO Auto-generated method stub
		List<PreRecruitment> preList = adminMapper.selPreForMain();
		return preList;
	}

	@Override
	public Integer insRejReasonInfo(RejReasonInfo rejInfo) {
		// TODO Auto-generated method stub
		Integer result = adminMapper.insRejReasonInfo(rejInfo);
		
		return result;
	}

	@Override
	public List<RejReasonInfo> selectRejRecAll() {
		// TODO Auto-generated method stub
		
		List<RejReasonInfo> rejReasonList = null;
		try {
			rejReasonList = adminMapper.selectRejRecAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return rejReasonList;

	}

	@Override
	public RejReasonInfo selectRejRec(int announcementCode) {
		// TODO Auto-generated method stub
		RejReasonInfo rejInfo = adminMapper.selectRejRec(announcementCode);
		
		return rejInfo;
	}

	@Override
	public Integer selectTotalDel() {
		// TODO Auto-generated method stub
		Integer result = adminMapper.selectTotalDel();
		
		return result;
	}

	@Override
	public Integer selectTotalUser() {
		// TODO Auto-generated method stub
		Integer result = adminMapper.selectTotalUser();
		
		return result;
	}

	@Override
	public Integer selectTotalCorUser() {
		// TODO Auto-generated method stub
		Integer result = adminMapper.selectTotalCorUser();
		
		return result;
	}
	
	@Override
	public List<UserVO> selUserInfoListPagingList(Pagination pagination) {
		// TODO Auto-generated method stub
		List<UserVO> userInfoList = adminMapper.selUserInfoListPagingList(pagination);
		
		return userInfoList;
	}

	@Override
	public List<UserVO> selCorUserInfoListPagingList(Pagination pagination) {
		// TODO Auto-generated method stub
		
		List<UserVO> coruserInfoList = adminMapper.selCorUserInfoListPagingList(pagination);
				
		return coruserInfoList;
	}
	
}
