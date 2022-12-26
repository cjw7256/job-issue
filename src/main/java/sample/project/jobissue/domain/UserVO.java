package sample.project.jobissue.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserVO {

	  private int userCode;
	  private int corCode;
	  private String userName;
	  private String userBirth;
	  private String userGender;
	  private String userTel;
	  private String userEmail;
	  private String userPassword;
	  private String userZipCode;
	  private String userAddress;
	  private String userDetailAddress;
	  private String resumeCode;
	  private String userType;
	  
}
