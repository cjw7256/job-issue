package sample.project.jobissue.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.commons.interceptor.AdminAccInterceptor;
import sample.project.jobissue.commons.interceptor.CorUserAccInterceptor;
import sample.project.jobissue.commons.interceptor.LoginInterceptor;
import sample.project.jobissue.commons.interceptor.MyPageAccessInterceptor;
import sample.project.jobissue.commons.interceptor.UserAccInterceptor;


@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new LoginInterceptor())
		.order(1).addPathPatterns("/**")
		.excludePathPatterns("/", "/user/logout", "/css/**", "/images/**", "/js/**"
				, "/user/register**", "/user/register/**", "/search", "/lists/**", "/lists", "/error");
	
		//관리자 전용 페이지
		registry.addInterceptor(new AdminAccInterceptor())
		.order(2)
		.addPathPatterns("/adminPage", "/adminPage**", "/adminPage/**");
		
		//일반 개인 회원
		registry.addInterceptor(new UserAccInterceptor())
		.order(3)
		.addPathPatterns("/resumes", "/resumes**", "/resumes/**", "/submit", "/submit**",
				"/submit/**");
		
		//기업 회원
		registry.addInterceptor(new CorUserAccInterceptor())
		.order(4)
		.addPathPatterns("/jobOpening", "/jobOpening**", "/jobOpening/**", 
				"/jobOpen", "/jobOpen**", "/jobOpen/**", 
				"/preJobOpen", "/preJobOpen**", "/preJobOpen/**",
				"/manageOpening", "/manageOpening**", "/manageOpening/**",
				"update/**", "/update", "/update**", 
				
				"/applicantManage", "/applicantManage**", "/applicantManage/**");
		
		//관리자 -> 마이페이지 접근x
		registry.addInterceptor(new MyPageAccessInterceptor())
		.order(5)
		.addPathPatterns("/user", "/user/**", "/user**")
		.excludePathPatterns("/user/logout", "/user/register**", "/user/register/**", "/user/login");
		
	}
}

