

package sample.project.jobissue.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sample.project.jobissue.domain.JobItem;
import sample.project.jobissue.domain.PageMaker;
import sample.project.jobissue.domain.Pagination;
import sample.project.jobissue.domain.ResumeItem;
import sample.project.jobissue.domain.SearchItem;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.repository.JobApplicationRepository;
import sample.project.jobissue.repository.JobRepository;
import sample.project.jobissue.session.SessionManager;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/lists")
public class JobController {

	private final JobRepository jobRepository;
	private final JobApplicationRepository jobApplicationRepository;

	// (http://localhost:8080/lists) ???????????? ?????????????????? ??? 
//	@GetMapping
//	public String lists(Model model) {
//		List<JobItem> jobList = jobRepository.selectAll();
//		model.addAttribute("lists",jobList);
//
//		return "/lists/lists";
//	}
//	
	
	//???????????? ????????? ?????? model ??????
	@ModelAttribute("pageMaker")
	public PageMaker searchItem(SearchItem si) {
		if (si == null) {
			si = new SearchItem();
		}
		PageMaker pageMaker = new PageMaker(si);
		
		return pageMaker;
	}
	
	//????????? ????????? ?????? lists
	@GetMapping
	public String lists(HttpServletRequest request, Model model, 
			@RequestParam(defaultValue = "1") int page, 
			HttpServletRequest req) {
		
		 // ??? ????????? ??? 
	    int totalCnt = jobRepository.selectTotalCnt();

	    // ???????????????  ??? ????????? ???, ?????? ???????????? ??????
	    Pagination pagination = new Pagination(totalCnt, page);

	    List<JobItem> jobList = jobRepository.selJobListPagingList(pagination);

	    model.addAttribute("jobList", jobList);
	    model.addAttribute("pagination", pagination);

	    log.info("{}", pagination);
	    log.info("jobList {}", jobList.get(0).getAnnouncementCode());
		
	    HttpSession session = req.getSession(false);
	    
		if(session != null) {
			UserVO userVO = (UserVO) session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
			model.addAttribute("userVO", userVO);
		} else {
			model.addAttribute("userVO", null);	
		}
	    
	    
		return "/lists/lists";	
	}


	@GetMapping("/{listAnnouncementCode}")
	public String list(Model model, 
			@PathVariable("listAnnouncementCode") int listAnnCode, 
			@ModelAttribute JobItem jobItem, 
			HttpServletRequest req) {
		
		jobItem = jobRepository.selectByAnnCode(listAnnCode);
		model.addAttribute("list", jobItem);
		HttpSession session = req.getSession(false);
		model.addAttribute("list", jobItem);
		
		if(session!=null) {
			UserVO userVO = (UserVO) session.getAttribute(SessionManager.SESSION_COOKIE_NAME);
			model.addAttribute("userVO", userVO);		
				if(session.getAttribute(SessionManager.SESSION_COOKIE_NAME)!=null) {
					session.setAttribute("corCord", jobItem);	
				}
		}else {
			model.addAttribute("userVO", null);	
		}
		return "/lists/list";
	}
	
	// ????????????API ???????????? ???????????? ???????????? ???????????? ?????????
//	 @PostConstruct //?????? ????????? ??????????????? ??? ????????? ????????? ??? ?????? ??????????????????
	@Transactional
	public void insertInit() throws IOException, ParseException {

		Set<String> corName = new HashSet<>();

		String jobItem = getJobData();
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonobj = (JSONObject) jsonParser.parse(jobItem);

		JSONArray arr = (JSONArray) jsonobj.get("GGJOBABARECRUSTM");
		JSONObject obj = (JSONObject) arr.get(1);
		JSONArray arr2 = (JSONArray) obj.get("row");

		//1)?????? ???????????? ????????????(???, ???????????? ?????? ??????????????? ???)
		//Parsing ????????? ?????? Insert 
		//Parsing -> Set (????????????) ????????? ??? ?????????. (????????????) -> Set?????? -> insert
		for (int cor = 0; cor < arr2.size(); cor++) {
			JSONObject corObj = (JSONObject) arr2.get(cor);

			if(corObj.get("ENTRPRS_NM") != null) {
				corName.add(corObj.get("ENTRPRS_NM").toString());
			}else {
				log.info("corObj {}", corObj);
			}
		}

		Iterator<String> iter = corName.iterator();
		
		UserVO initCor = new UserVO();
		
		while(iter.hasNext()) {
			initCor.setUserName(iter.next());
			
			UserVO user = jobRepository.insertCorInitInfo(initCor);
			
			//??????????????? -> ???????????? ????????? ????????? ??????
			//???????????? ????????? ??????
			jobRepository.insertUserInfoAsCor(user);
		}

		log.info("?????? ????????? ?????? ??????");
		log.info("jobitem ?????? ??????");
		

		//2)???????????? ????????? ??????(??? ??? ??????????????? ??????, 
		for (int j = 0; j < arr2.size(); j++) {
			JSONObject obj2 = (JSONObject) arr2.get(j);
			JobItem jobItemObj = new JobItem();

			if(obj2.get("ENTRPRS_NM") != null) { //???????????? null??? ?????? ??????
				jobItemObj.setCorName(obj2.get("ENTRPRS_NM").toString());

				if(obj2.get("PBANC_CONT") != null) {
					jobItemObj.setAnnouncement(obj2.get("PBANC_CONT").toString());
				}
				if(obj2.get("RECRUT_FIELD_CD_NM") != null) {
					jobItemObj.setRecruitFieldCode(obj2.get("RECRUT_FIELD_CD_NM").toString());
				}
				if(obj2.get("RECRUT_FIELD_NM") != null) {
					jobItemObj.setRecruitField(obj2.get("RECRUT_FIELD_NM").toString());
				}
				if(obj2.get("SALARY_COND") != null) {
					jobItemObj.setSalary(obj2.get("SALARY_COND").toString());
				}
				if(obj2.get("CAREER_CD_NM") != null) {
					jobItemObj.setCareerCode(obj2.get("CAREER_CD_NM").toString());
				}
				if(obj2.get("EMPLMNT_PSNCNT") != null) {
					//?????? ???????????? ????????? ????????? '???'??? ???????????? ????????? ??????... ?????? ????????? ????????? ?????????!
					if(obj2.get("EMPLMNT_PSNCNT").toString().contains("???")) {
						String[] tempList = obj2.get("EMPLMNT_PSNCNT").toString().split("???");
						jobItemObj.setRecruitPerson(tempList[0]);
					}else {
						jobItemObj.setRecruitPerson(obj2.get("EMPLMNT_PSNCNT").toString());
					}
				}

				if(obj2.get("RCPT_BGNG_DE") != null) {
					String startDate = obj2.get("RCPT_BGNG_DE").toString();
					//??????..?????? ??????????????? ?????? ???????????? yyyymmdd??? ????????? ????????????... ????????? ????????????
					if(!startDate.contains("-")) {
						startDate = startDate.substring(0,4)+"-"+startDate.substring(4, 6)+"-"+startDate.substring(6,8);
					}
					jobItemObj.setReceiptStartDate(LocalDate.parse(startDate));
				}

				if(obj2.get("RCPT_END_DE") != null) {
					String endDate = obj2.get("RCPT_END_DE").toString();
					//??????..?????? ??????????????? ?????? ???????????? yyyymmdd???
					if(!endDate.contains("-")) {
						endDate = endDate.substring(0,4)+"-"+endDate.substring(4, 6)+"-"+endDate.substring(6,8);
					}
					jobItemObj.setReceiptEndDate(LocalDate.parse(endDate));
				}


				//					log.info("{}",jobItemObj);
				JobItem afterInsertObj = jobRepository.insertRecruitInit(jobItemObj);	

				//3)???????????? ??????... ??? ????????? ?????? ???????????? ?????? ??????????????????					
				//???????????? //???????????? //???????????? -> ?????? null??? ?????? ??????
				if(obj2.get("PBANC_FORM_CD_NM") != null) {
					String empList = obj2.get("PBANC_FORM_CD_NM").toString();
					jobRepository.insertMulEmp(afterInsertObj.getAnnouncementCode(), Arrays.asList(empList.split(",")));
				}

				if(obj2.get("WORK_REGION_CD_CONT") != null) {
					String workAreaList = obj2.get("WORK_REGION_CD_CONT").toString();
					jobRepository.insertMulWork(afterInsertObj.getAnnouncementCode(), Arrays.asList(workAreaList.split(",")));
				}

				if(obj2.get("ACDMCR_CD_NM") != null) {
					String acaList = obj2.get("ACDMCR_CD_NM").toString();
					jobRepository.insertMulAca(afterInsertObj.getAnnouncementCode(), Arrays.asList(acaList.split(",")));
				}

			}
		} // ???????????? null??? ?????? ????????? ?????????
		log.info("jobitem ??? ?????? ?????? ?????? ??????");
	}

	
	// ????????????????????? ???????????? ???????????? API ???????????? ???????????? ?????????.
	public static String getJobData() throws IOException {
		String jobItem = "";

		StringBuilder urlBuilder = new StringBuilder("https://openapi.gg.go.kr/GGJOBABARECRUSTM"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("Key", "UTF-8") + "=1195502f8abe406db404b79b30edf223");/* Service Key ?????? ??????*/
		urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("pIndex","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("pSize","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8"));

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

		jobItem = sb.toString();

		return jobItem;
	}
}