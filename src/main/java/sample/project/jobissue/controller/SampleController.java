package sample.project.jobissue.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
	
	@RequestMapping("/rest1")
	public String rest1() {
		return "rest1";
	}
	
	@RequestMapping("/rest2")
	public String rest2() {
		return "rest2";
	}
	
	@RequestMapping("/food/list")
	public String foodList() {
		return "foodlist";
	}
}
