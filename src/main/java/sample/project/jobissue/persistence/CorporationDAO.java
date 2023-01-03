package sample.project.jobissue.persistence;

import sample.project.jobissue.domain.CorporationVO;
import sample.project.jobissue.domain.UserVO;
import sample.project.jobissue.validation.LoginForm;

public interface CorporationDAO {
	CorporationVO findCopByName(String copName) throws Exception;
}
