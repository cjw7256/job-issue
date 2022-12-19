package sample.project.jobissue.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.repository.JobRepository;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/lists")
public class JobController {

	private final JobRepository jobRepository;

	@PostMapping("/list")
	public String list2(Model model, @RequestParam int listCorporationNo) {
		JobItem jobItem = jobRepository.selectByCorporationNo(listCorporationNo);
		model.addAttribute("list", jobItem);
		
		return "list";
	}
	
	@GetMapping("/{listCorporationNo}")
	public String list(Model model, @PathVariable("listCorporationNo") int listCorporationNo) {
		JobItem jobItem = jobRepository.selectByCorporationNo(listCorporationNo);
		model.addAttribute("list", jobItem);
		
		return "list";
	}
	
	// (http://localhost:8080/lists) 서버켜고 주소입력하면 뜸 
	@GetMapping
	public String lists(Model model, HttpServletRequest req) {
		List<JobItem> jobList = jobRepository.selectAll();
		model.addAttribute("lists",jobList);
		
		return "lists";
	}
	
	// 채용공고API 데이터를 파싱해서 오라클에 저장하는 클래스
//	@PostConstruct
	public void insertInit() throws IOException, ParseException {
		String jobItem = getJobData();
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonobj = (JSONObject) jsonParser.parse(jobItem);

		JSONArray arr = (JSONArray) jsonobj.get("GGJOBABARECRUSTM");
		for (int i = 0; i < arr.size(); i++) {

			if (i == 1) {
				JSONObject obj = (JSONObject) arr.get(i);

				JSONArray arr2 = (JSONArray) obj.get("row");
				for (int j = 0; j < arr2.size(); j++) {
					JSONObject obj2 = (JSONObject) arr2.get(j);
					JobItem jobItem1 = new JobItem();
					jobItem1.CorporationName = (String) obj2.get("ENTRPRS_NM");
					jobItem1.Announcement = (String) obj2.get("PBANC_CONT");
					jobItem1.Salary = (String) obj2.get("SALARY_COND");
					jobItem1.AnnouncementTypeCode = (String) obj2.get("PBANC_FORM_CD_NM");
					jobItem1.AnnouncementType = (String) obj2.get("PBANC_FORM_DIV");
					jobItem1.Working_area_Code = (String) obj2.get("WORK_REGION_CD_CONT");
					jobItem1.Working_area = (String) obj2.get("WORK_REGION_CONT");
					jobItem1.Career_code = (String) obj2.get("CAREER_CD_NM");
					jobItem1.Career = (String) obj2.get("CAREER_DIV");
					jobItem1.Academic_record_code = (String) obj2.get("ACDMCR_CD_NM");
					jobItem1.Academic_record = (String) obj2.get("ACDMCR_DIV");
					jobItem1.recruitment_code = (String) obj2.get("RECRUT_FIELD_CD_NM");
					jobItem1.recruitment = (String) obj2.get("RECRUT_FIELD_NM");
					jobItem1.Recruitment_Person = (String) obj2.get("EMPLMNT_PSNCNT");
					jobItem1.receipt_start = (String) obj2.get("RCPT_BGNG_DE");
					jobItem1.receipt_end = (String) obj2.get("RCPT_END_DE");

					jobRepository.insert(jobItem1);
				}
			}
		}
	}

	// 경기데이터드림 사이트의 채용공고 API 데이터를 읽어오는 클래스.
	public static String getJobData() throws IOException {
		String JobItem = "";
		
		StringBuilder urlBuilder = new StringBuilder("https://openapi.gg.go.kr/GGJOBABARECRUSTM"); /* URL */
		urlBuilder.append(
				"?" + URLEncoder.encode("serviceKey", "UTF-8") + "=929c622403f44d6bb2a2a999bc9e742a"); /* Service Key */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();

		JobItem = sb.toString();

		return JobItem;
	}
}
