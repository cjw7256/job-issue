package sample.project.jobissue.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import sample.project.jobissue.domain.PreRecruitment;
import sample.project.jobissue.repository.PreRecruitmentRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/jobOpening")
public class JobOpeningController {

	private final PreRecruitmentRepository preRecruitRepository;
	
	
	@GetMapping
	public String jobOpening(Model model){
		model.addAttribute("preRecruit", new PreRecruitment());
		return "/corporation/jobOpening";
	}
	
//	@ModelAttribute("careerCodes")
//	public Map<String, String> careerCodes(){
////		Map<String, String> options = new HashMap<>();
//		Map<String, String> careerCode = new LinkedHashMap<>();
//		
//		careerCode.put("1번", "탄수화물");
//		careerCode.put("2번", "단백질");
//		careerCode.put("3번", "지방");
//		
//		return careerCode;
//	}
	//1.post형식 작성
	//2. @modelAttribute 로  jobitem 바로 매핑
	//3. 매핑한 객체의 변수를 jobitem.~으로 불러서 쿼리문의 매개변수로 넣기
	//4. xml에서 작성하는 쿼리문은 1. 코드를 제외한 정보가 임시 저장 테이블로 넘어가는 쿼리문
	//						2. 각종 코드가 각각 저장이 되는 쿼리문(다중선택 옵션을 저장하는 테이블 
	//						- jobmapper.xml 에서 insertmulEMP, insertmultWork~ 참고)
	
}
