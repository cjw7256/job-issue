package sample.project.jobissue.repository.mybatis;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import sample.project.jobissue.domain.FileStoreDto;
import sample.project.jobissue.repository.FileStoreRepository;

@Repository
@RequiredArgsConstructor
public class MybatisFileStoreRepository implements FileStoreRepository{
	
	private final FileStoreMapper fileStoreMapper;
	
	public int insert(FileStoreDto fileStoreDto) {
		return fileStoreMapper.insert(fileStoreDto);
	}

	@Override
	public FileStoreDto selectFileInfo(String tableCode, String pkId) {
		// TODO Auto-generated method stub
		return fileStoreMapper.selectFileInfo(tableCode, pkId);
	}

	@Override
	public boolean deleteImage(int userCode) {
		// TODO Auto-generated method stub
		
		boolean result = false;
		try {
			fileStoreMapper.deleteImage(userCode);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}

	@Override
	public FileStoreDto selectImageInfo(int userCode) {
		// TODO Auto-generated method stub
		return fileStoreMapper.selectImageInfo(userCode);
	}
}
