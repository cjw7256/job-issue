package sample.project.jobissue.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.FileStoreDto;

@Mapper
public interface FileStoreMapper {
	public Integer insert(FileStoreDto fileStoreDto);
	
	public FileStoreDto selectFileInfo(@Param("tableCode") String tableCode, @Param("pkId") String pkId);
	
	public FileStoreDto selectImageInfo(int userCode);
	
	public void deleteImage(int userCode);
}
