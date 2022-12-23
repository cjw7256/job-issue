package sample.project.jobissue.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserVO {

//    private String userId;
//    private String userPw;
//    private String userName;
//    private String userEmail;
//    private LocalDateTime userJoinDate;
//    private LocalDateTime userLoginDate;
//    private String userSignature;
//    private String userImg;
//    private int userPoint;

	  private int userCode;
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
	  private String corCode;
	  private String userType;
}
