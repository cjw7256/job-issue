package sample.project.jobissue.service;

import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.validation.LoginForm;
import sample.project.jobissue.validation.UserRegisterForm;

public interface UserService {
	 void register(UserRegisterForm userRegisterForm) throws Exception;
	 
	 UserVO login(LoginForm loginForm) throws Exception;
	 
	 UserVO findUserByEmail(String userEmail) throws Exception;
	 
	 UserVO findUserByTel(String userTel) throws Exception;
	 
	 void userPasswordUpdate(String userPassword, String userEmail) throws Exception;
	 
	 Integer modifyUserInfo(UserRegisterForm userRegisterForm) throws Exception;
	 
	 Integer dropUserByEmail(String userEmail) throws Exception;
	 
	 
}
