package sample.project.jobissue.controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ErrorController {

	@RequestMapping("/error/400")
	public String error400(Model model, HttpServletRequest req) {
		log.info("error/400");
		log.info("request uri {}", req.getRequestURI());
		
		return "error/400";
	}
	
	@RequestMapping("/error/404")
	public String error404() {
		log.info("error/404");
		
		return "error/404";
	}
	
	@RequestMapping("/error/500")
	public String error500(Model model, HttpServletRequest req) {
		log.info("error/500");
		log.info("request uri {}", req.getRequestURI());

		return "error/500";
	}

}
