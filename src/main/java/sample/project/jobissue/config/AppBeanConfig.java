package sample.project.jobissue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sample.project.jobissue.repository.FileStoreRepository;
import sample.project.jobissue.repository.JobApplicationRepository;
import sample.project.jobissue.repository.JobRepository;
import sample.project.jobissue.repository.ResumeRepository;
import sample.project.jobissue.repository.mybatis.FileStoreMapper;
import sample.project.jobissue.repository.mybatis.JobApplicationMapper;
import sample.project.jobissue.repository.mybatis.JobMapper;
import sample.project.jobissue.repository.mybatis.MybatisFileStoreRepository;
import sample.project.jobissue.repository.mybatis.MybatisJobApplicationRepository;
import sample.project.jobissue.repository.mybatis.MybatisJobRepository;
import sample.project.jobissue.repository.mybatis.MybatisResumeRepository;
import sample.project.jobissue.repository.mybatis.ResumeMapper;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppBeanConfig {
	private final JobMapper jobMapper;
	private final ResumeMapper resumeMapper;
	private final JobApplicationMapper jobApplicationMapper;
	private final FileStoreMapper fileStoreMapper;
	
	@Bean
	public JobRepository jobRepository() {
		return new MybatisJobRepository(jobMapper);
	}

	@Bean
	public ResumeRepository resumeRepository() {
		return new MybatisResumeRepository(resumeMapper);
	}
	
	@Bean
	public JobApplicationRepository jobApplicationRepository() {
		return new MybatisJobApplicationRepository(jobApplicationMapper);
	}
	
	@Bean
	public FileStoreRepository fileStoreRepository() {
		return new MybatisFileStoreRepository(fileStoreMapper);
	}
	
}
