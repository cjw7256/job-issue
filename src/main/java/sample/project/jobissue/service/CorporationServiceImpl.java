package sample.project.jobissue.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sample.project.jobissue.domain.CorporationVO;
import sample.project.jobissue.persistence.CorporationDAO;

@Service
@RequiredArgsConstructor
public class CorporationServiceImpl implements CorporationService{
	
	private final CorporationDAO corporationDAO; 
	
	@Override
	public CorporationVO findCopByName(String copName) throws Exception {
		// TODO Auto-generated method stub
		CorporationVO corporationVO = corporationDAO.findCopByName(copName);
		return corporationVO;
	}

	
}
