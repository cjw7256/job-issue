package sample.project.jobissue.repository;

import org.apache.ibatis.annotations.Param;

import sample.project.jobissue.domain.FileStoreDto;

public interface FileStoreRepository {
	public int insert(FileStoreDto fileStoreDto);
	
	public FileStoreDto selectFileInfo(String tableCode, String pkId);
	
	public FileStoreDto selectImageInfo(int userCode);
	
	public boolean deleteImage(int userCode);
}
