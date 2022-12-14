package sample.project.jobissue.persistence;


import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.project.jobissue.domain.LoginDTO;
import sample.project.jobissue.domain.UserTypeCode;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.repository.mybatis.UserMapper;
import sample.project.jobissue.validation.LoginForm;
import sample.project.jobissue.validation.UserRegisterForm;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final UserMapper userMapper;

    // 회원가입처리
    @Override
    public void register(UserRegisterForm userRegisterForm) throws Exception {
    	try {
    		if(UserTypeCode.USER_TYPE_PERSONAL.equals(userRegisterForm.getUserType())) {
    			userMapper.register(userRegisterForm);		
    		}
    		if(UserTypeCode.USER_TYPE_CORPORATION.equals(userRegisterForm.getUserType())) {
    			userMapper.registerCop(userRegisterForm);		
    		}
    	} catch (Exception e) {
			// TODO: handle exception
    		log.info("register error {}", e.getMessage());
    		throw e;
		}
    }
    
 // 로그인 처리
    @Override
    public UserVO login(LoginForm loginForm) throws Exception {
    	UserVO userVO = userMapper.login(loginForm);
		return userVO;
    }

	@Override
	public UserVO findUserByEmail(String userEmail) throws Exception {
		// TODO Auto-generated method stub
		UserVO userVO = userMapper.selectUserByEmail(userEmail);
		return userVO;
	}
	
	@Override
	public UserVO findUserByTel(String userTel) throws Exception {
		// TODO Auto-generated method stub
		UserVO userVO = userMapper.selectUserByTel(userTel);
		return userVO;
	}

	@Override
	public Integer modifyUserInfo(UserRegisterForm userRegisterForm) throws Exception {
		// TODO Auto-generated method stub
		Integer result = userMapper.updateUserInfo(userRegisterForm);
		return result;
	}

	@Override
	public Integer dropUserByEmail(String userEmail) throws Exception {
		// TODO Auto-generated method stub
		Integer result = userMapper.deleteUserByEmail(userEmail);
		return result;
	}

	@Override
	public void userPasswordUpdate(String userPassword, String userEmail) throws Exception {
		// TODO Auto-generated method stub
//		UserVO userVO = 
		userMapper.userPasswordUpdate(userPassword, userEmail);
	}

}
