package sample.project.jobissue.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.LoginDTO;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.validation.LoginForm;
import sample.project.jobissue.validation.UserRegisterForm;

@Mapper
public interface UserMapper {
	public Integer register(UserRegisterForm userRegisterForm);
	public UserVO login(LoginForm loginForm);
	public UserVO selectUserByEmail(String userEmail);
	public UserVO selectUserByTel(String userTel);
	Integer updateUserInfo(@Param("userInfo") UserRegisterForm userRegisterForm);
	Integer deleteUserByEmail(@Param("userEmail") String userEmail);

}
