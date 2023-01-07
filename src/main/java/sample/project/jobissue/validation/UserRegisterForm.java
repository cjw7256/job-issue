package sample.project.jobissue.validation;

import lombok.Data;


@Data
public class UserRegisterForm {
	  private int userCode;
	  private String userName;
	  private String userBirth;
	  private String userGender;
	  private String userTel;
	  private String userEmail;
	  private String userPassword;
	  private String userPasswordConfirm;
	  private String userAddress;
	  private String userDetailAddress;
	  private String userZipCode;
	  private String resumeCode;
	  private int corCode;
	  private String userType;
	  private boolean agreeTerms;
	  
	  private String corName;
}
