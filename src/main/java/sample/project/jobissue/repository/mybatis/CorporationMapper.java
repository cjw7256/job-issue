package sample.project.jobissue.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;

import sample.project.jobissue.domain.CorporationVO;

@Mapper
public interface CorporationMapper {
	CorporationVO selectCopByName(String corName) throws Exception;
}
