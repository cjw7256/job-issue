package sample.project.jobissue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sample.project.jobissue.repository.JobRepository;
import sample.project.jobissue.repository.mybatis.JobMapper;
import sample.project.jobissue.repository.mybatis.MybatisJobRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppBeanConfig {
	private final JobMapper jobMapper;
	
	@Bean
	public JobRepository jobRepository() {
		return new MybatisJobRepository(jobMapper);
	}
}
