package sample.project.jobissue.service;

import sample.project.jobissue.domain.CorporationVO;

public interface CorporationService {
	CorporationVO findCopByName(String copName) throws Exception;
}
