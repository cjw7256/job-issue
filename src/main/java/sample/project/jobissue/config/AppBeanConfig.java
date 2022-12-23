package sample.project.jobissue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import sample.project.jobissue.repository.JobRepository;
import sample.project.jobissue.repository.ResumeRepository;
import sample.project.jobissue.repository.mybatis.JobMapper;
import sample.project.jobissue.repository.mybatis.MybatisJobRepository;
import sample.project.jobissue.repository.mybatis.MybatisResumeRepository;
import sample.project.jobissue.repository.mybatis.ResumeMapper;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppBeanConfig {
	private final JobMapper jobMapper;
	private final ResumeMapper resumeMapper;
		
	@Bean
	public JobRepository jobRepository() {
		return new MybatisJobRepository(jobMapper);
	}
	
	
	
	@Bean
	public ResumeRepository resumeRepository() {
		return new MybatisResumeRepository(resumeMapper);
	}
}
