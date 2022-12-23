package sample.project.jobissue.service;

import sample.project.jobissue.domain.LoginDTO;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.validation.LoginForm;
import sample.project.jobissue.validation.UserRegisterForm;

public interface UserService {
	 void register(UserRegisterForm userRegisterForm) throws Exception;
	 
	 UserVO login(LoginForm loginForm) throws Exception;
	 
	 UserVO findUserByEmail(String userEmail) throws Exception;
	 
}
