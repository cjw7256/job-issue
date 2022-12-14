package sample.project.jobissue.repository.mybatis;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.ResumeItem;
import sample.project.jobissue.domain.SubmitResumeItem;
import sample.project.jobissue.repository.JobApplicationRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MybatisJobApplicationRepository implements JobApplicationRepository {

	private final JobApplicationMapper jobApplicationMapper;


	@Override
	public ResumeItem selectByUserResume(int userCode) {
		// TODO Auto-generated method stub
		ResumeItem resumeItem = jobApplicationMapper.selectByUserResume(userCode);
		return resumeItem;
	}




	@Override
	public JobItem insertSubmitResume(int corCode, int announcementCode, int userCode) {
		// TODO Auto-generated method stub
		jobApplicationMapper.insertSubmitResume(corCode, announcementCode, userCode);
		return null;
	}




	@Override
	public SubmitResumeItem selectByUserSubmitResume(int userCode, int announcementCode) {
		// TODO Auto-generated method stub
		SubmitResumeItem submitResumeItem = jobApplicationMapper.selectByUserSubmitResume(userCode, announcementCode);
		return submitResumeItem;
	}

	
	

}