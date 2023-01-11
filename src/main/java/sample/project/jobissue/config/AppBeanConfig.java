package sample.project.jobissue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sample.project.jobissue.repository.AdminRepository;
import sample.project.jobissue.repository.JobApplicationRepository;
import sample.project.jobissue.repository.JobRepository;
import sample.project.jobissue.repository.PreRecruitmentRepository;
import sample.project.jobissue.repository.ResumeRepository;
import sample.project.jobissue.repository.mybatis.AdminMapper;
import sample.project.jobissue.repository.mybatis.JobApplicationMapper;
import sample.project.jobissue.repository.mybatis.JobMapper;
import sample.project.jobissue.repository.mybatis.MybatisAdminRepository;
import sample.project.jobissue.repository.mybatis.MybatisJobApplicationRepository;
import sample.project.jobissue.repository.mybatis.MybatisJobRepository;
import sample.project.jobissue.repository.mybatis.MybatisPreRecruitment;
import sample.project.jobissue.repository.mybatis.MybatisResumeRepository;
import sample.project.jobissue.repository.mybatis.PreRecruitmentMapper;
import sample.project.jobissue.repository.mybatis.ResumeMapper;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppBeanConfig {
	private final JobMapper jobMapper;
	private final ResumeMapper resumeMapper;
	private final AdminMapper adminMapper;
	private final JobApplicationMapper jobApplicationMapper;
	private final PreRecruitmentMapper preRecruitMapper;
	
	@Bean
	public JobRepository jobRepository() {
		return new MybatisJobRepository(jobMapper);
	}

	@Bean
	public ResumeRepository resumeRepository() {
		return new MybatisResumeRepository(resumeMapper);
	}
	
	@Bean
	public AdminRepository adminRepository() {
		return new MybatisAdminRepository(adminMapper);
	}
	
	@Bean
	public JobApplicationRepository jobApplicationRepository() {
		return new MybatisJobApplicationRepository(jobApplicationMapper);
	}
	
//	@Bean
//	public PreRecruitmentRepository preRecruitmentRepository() {
//		return new MybatisPreRecruitment(preRecruitMapper);
//	}
}
