package sample.project.jobissue.persistence;

import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.validation.LoginForm;
import sample.project.jobissue.validation.UserRegisterForm;

public interface UserDAO {
	void register(UserRegisterForm userRegisterForm) throws Exception;

	UserVO login(LoginForm loginForm) throws Exception;

	UserVO findUserByEmail(String userEmail) throws Exception;

	UserVO findUserByTel(String userTel) throws Exception;
	
	Integer modifyUserInfo(UserRegisterForm userRegisterForm) throws Exception;
	
	 Integer dropUserByEmail(String userEmail) throws Exception;

	 void userPasswordUpdate(@Param("userPassword") String userPassword, @Param("userEmail")String userEmail) throws Exception;
}
