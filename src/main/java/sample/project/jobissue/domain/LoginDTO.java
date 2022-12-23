package sample.project.jobissue.domain;

import lombok.Data;

@Data
public class LoginDTO {
	private String userEmail;
	private String userPassword;
	private boolean useCookie;
}
