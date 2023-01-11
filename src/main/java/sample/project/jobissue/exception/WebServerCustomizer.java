package sample.project.jobissue.exception;


import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;

public class WebServerCustomizer 
implements WebServerFactoryCustomizer<ConfigurableWebServerFactory>{

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		// TODO Auto-generated method stub
		
		ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
		ErrorPage error400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400");
		ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");
	
		ErrorPage errorRE = new ErrorPage(RuntimeException.class, "/error/500");
		
		factory.addErrorPages(error404, error400,error500,  errorRE);
	
	}

}
