package sample.project.jobissue.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sample.project.jobissue.domain.LoginDTO;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.persistence.UserDAO;
import sample.project.jobissue.validation.LoginForm;
import sample.project.jobissue.validation.UserRegisterForm;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    // 회원 가입 처리
    @Override
    public void register(UserRegisterForm userRegisterForm) throws Exception {
        userDAO.register(userRegisterForm);
    }

    // 로그인 처리
    @Override
	public UserVO login(LoginForm loginForm) throws Exception {
    	return userDAO.login(loginForm);
    }
}
