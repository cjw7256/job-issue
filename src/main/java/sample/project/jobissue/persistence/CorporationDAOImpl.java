package sample.project.jobissue.persistence;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import sample.project.jobissue.domain.CorporationVO;
import sample.project.jobissue.repository.mybatis.CorporationMapper;

@Repository
@RequiredArgsConstructor
public class CorporationDAOImpl implements CorporationDAO{

	private final CorporationMapper corporationMapper;
	
	@Override
	public CorporationVO findCopByName(String copName) throws Exception {
		// TODO Auto-generated method stub
		CorporationVO corporationVO = corporationMapper.selectCopByName(copName);
		return corporationVO;
	}
	
}
