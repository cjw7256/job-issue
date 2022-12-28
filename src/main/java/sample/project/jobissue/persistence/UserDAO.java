package sample.project.jobissue.persistence;

import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.validation.LoginForm;
import sample.project.jobissue.validation.UserRegisterForm;

public interface UserDAO {
	  void register(UserRegisterForm userRegisterForm) throws Exception;
	  
	  UserVO login(LoginForm loginForm) throws Exception;
}
