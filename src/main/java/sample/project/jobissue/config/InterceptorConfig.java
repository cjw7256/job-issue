package sample.project.jobissue.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.commons.interceptor.LoginInterceptor;


@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new LoginInterceptor())
		.order(1).addPathPatterns("/**")
		.excludePathPatterns("/", "/user/logout", "/css/*", "/images/*", "/js/*"
				, "/user/register**", "/user/register/*", "/search","/lists/**", "/error");
	}
}

