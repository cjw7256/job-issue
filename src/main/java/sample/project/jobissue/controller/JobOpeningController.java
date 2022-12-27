package sample.project.jobissue.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.AcademicRecordCode;
import sample.project.jobissue.domain.CareerCode;
import sample.project.jobissue.domain.EmployTypeCode;
import sample.project.jobissue.domain.PreRecruitment;
import sample.project.jobissue.domain.RecruitFieldCode;
import sample.project.jobissue.domain.WorkingAreaCode;
import sample.project.jobissue.repository.PreRecruitmentRepository;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/jobOpening")
public class JobOpeningController {

	private final PreRecruitmentRepository preRecruitRepository;
	
	
	@GetMapping
	public String jobOpening(Model model, HttpServletRequest req){
		PreRecruitment preRecruit = new PreRecruitment();
		model.addAttribute("preRecruit", preRecruit);
		
		return "/corporation/jobOpening";
	}
	
	@ModelAttribute("careerCodes")
    public List<CareerCode> careerCodes(Model model) {
        List<CareerCode> careerCode = new ArrayList<>();
        careerCode.add(new CareerCode("01", "무관"));
        careerCode.add(new CareerCode("02", "신입"));
        careerCode.add(new CareerCode("03", "경력"));
        careerCode.add(new CareerCode("04", "신입/경력"));
        
        
        return careerCode;
    }
	
	@ModelAttribute("employTypeCodes")
    public List<EmployTypeCode> employTypeCodes() {
        List<EmployTypeCode> employTypeCodes = new ArrayList<>();
        employTypeCodes.add(new EmployTypeCode("01", "정규직"));
        employTypeCodes.add(new EmployTypeCode("02", "계약직"));
        employTypeCodes.add(new EmployTypeCode("03", "인턴직"));
        employTypeCodes.add(new EmployTypeCode("04", "프리랜서"));
        employTypeCodes.add(new EmployTypeCode("05", "기타/아르바이트"));
        return employTypeCodes;
    }
	
	@ModelAttribute("recruitFieldCodes")
    public List<RecruitFieldCode> recruitFieldCodes(Model model) {
        List<RecruitFieldCode> recruitFieldCodes = new ArrayList<>();
        recruitFieldCodes.add(new RecruitFieldCode("01", "경영ㆍ사무ㆍ금융ㆍ보험직"));
        recruitFieldCodes.add(new RecruitFieldCode("02", "연구직 및 공학 기술직"));
        recruitFieldCodes.add(new RecruitFieldCode("03", "교육ㆍ법률ㆍ사회복지ㆍ경찰ㆍ소방직 및 군인"));
        recruitFieldCodes.add(new RecruitFieldCode("04", "보건ㆍ의료직"));
        recruitFieldCodes.add(new RecruitFieldCode("05", "예술ㆍ디자인ㆍ방송ㆍ스포츠직"));
        recruitFieldCodes.add(new RecruitFieldCode("06", "미용ㆍ여행ㆍ숙박ㆍ음식ㆍ경비ㆍ청소직"));
        recruitFieldCodes.add(new RecruitFieldCode("07", "영업ㆍ판매ㆍ운전ㆍ운송직"));
        recruitFieldCodes.add(new RecruitFieldCode("08", "건설ㆍ채굴직"));
        recruitFieldCodes.add(new RecruitFieldCode("09", "설치ㆍ정비ㆍ생산직"));
        recruitFieldCodes.add(new RecruitFieldCode("10", "농림어업직"));
       
        //model.addAttribute("recruitFieldCodes", recruitFieldCodes);
        return recruitFieldCodes;
    }
	
	@ModelAttribute("academicRecordCodes")
    public List<AcademicRecordCode> academicRecordCodes() {
        List<AcademicRecordCode> academicRecordCode = new ArrayList<>();
        academicRecordCode.add(new AcademicRecordCode("00", "학력무관"));
        academicRecordCode.add(new AcademicRecordCode("01", "초졸이하"));
        academicRecordCode.add(new AcademicRecordCode("02", "중졸"));
        academicRecordCode.add(new AcademicRecordCode("03", "고졸"));
        academicRecordCode.add(new AcademicRecordCode("04", "대졸(2~3년)"));
        academicRecordCode.add(new AcademicRecordCode("05", "대졸(4년)"));
        academicRecordCode.add(new AcademicRecordCode("06", "석사"));
        academicRecordCode.add(new AcademicRecordCode("07", "박사"));
        return academicRecordCode;
    }
	
	@ModelAttribute("workingAreaCodes")
    public List<WorkingAreaCode> workingAreaCodes() {
        List<WorkingAreaCode> waCode = new ArrayList<>();
        waCode.add(new WorkingAreaCode("1000","전국"));       
        waCode.add(new WorkingAreaCode("1100","서울특별시"));
        waCode.add(new WorkingAreaCode("2800","인천광역시"));
        waCode.add(new WorkingAreaCode("4100","경기전체"));
        waCode.add(new WorkingAreaCode("4111","수원시"));
        waCode.add(new WorkingAreaCode("4113","성남시"));
        waCode.add(new WorkingAreaCode("4115","의정부시"));
        waCode.add(new WorkingAreaCode("4117","안양시"));
        waCode.add(new WorkingAreaCode("4119","부천시"));
        waCode.add(new WorkingAreaCode("4121","광명시"));
        waCode.add(new WorkingAreaCode("4122","평택시"));
        waCode.add(new WorkingAreaCode("4125","동두천시"));
        waCode.add(new WorkingAreaCode("4127","안산시"));
        waCode.add(new WorkingAreaCode("4128","고양시"));
        waCode.add(new WorkingAreaCode("4129","과천시"));
        waCode.add(new WorkingAreaCode("4131","구리시"));
        waCode.add(new WorkingAreaCode("4136","남양주시"));
        waCode.add(new WorkingAreaCode("4137","오산시"));
        waCode.add(new WorkingAreaCode("4139","시흥시"));
        waCode.add(new WorkingAreaCode("4141","군포시"));
        waCode.add(new WorkingAreaCode("4143","의왕시"));
        waCode.add(new WorkingAreaCode("4145","하남시"));
        waCode.add(new WorkingAreaCode("4146","용인시"));
        waCode.add(new WorkingAreaCode("4148","파주시"));
        waCode.add(new WorkingAreaCode("4150","이천시"));
        waCode.add(new WorkingAreaCode("4155","안성시"));
        waCode.add(new WorkingAreaCode("4157","김포시"));
        waCode.add(new WorkingAreaCode("4159","화성시"));
        waCode.add(new WorkingAreaCode("4161","광주시"));
        waCode.add(new WorkingAreaCode("4163","양주시"));
        waCode.add(new WorkingAreaCode("4165","포천시"));
        waCode.add(new WorkingAreaCode("4167","여주시"));
        waCode.add(new WorkingAreaCode("4180","연천군"));
        waCode.add(new WorkingAreaCode("4182","가평군"));
        waCode.add(new WorkingAreaCode("4183","양평군"));
       
        return waCode;
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
