package sample.project.jobissue.validation;

import lombok.Data;

@Data
public class PasswordForm {
	
	private String userPassword;
	private String newPassword;
	private String checkPassword;
	
}
