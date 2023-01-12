package sample.project.jobissue.file;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import sample.project.jobissue.domain.UploadFile;

@Component
public class FileStoreManager {
	
	@Value("${upload.file.path}")
	private String fileDir;
	
	public String getFileFullPath(String fileName) {
		return fileDir + fileName; //저장된 폴더경로 + 파일이름
	}
	
	public String getFilePath() {
		return fileDir;
	}
	
	public List<UploadFile> saveFiles(List<MultipartFile> files) throws IllegalStateException, IOException {
		
		List<UploadFile> uploadFiles = new ArrayList<UploadFile>();
		for(MultipartFile file : files) {
			if(!file.isEmpty()) {
				UploadFile uploadFile = saveFile(file);
				uploadFiles.add(uploadFile);
			}
		}
		return uploadFiles;
	}
	
	public UploadFile saveFile(MultipartFile file) throws IllegalStateException, IOException {
		if(file.isEmpty()) {
			return null;
		}
		
		String orgFileName = file.getOriginalFilename();
		String realFileName = getRealFileName(orgFileName);
		
		file.transferTo(new File(getFileFullPath(realFileName)));
		
		UploadFile uploadFile = new UploadFile(orgFileName, realFileName);
		return uploadFile;
	}
	
	public String getRealFileName(String fileName) {
		String realFileName = UUID.randomUUID().toString();
		String ext = getExtension(fileName);
		return realFileName + "." + ext;
	}
	
	public String getExtension(String fileName) {
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		return ext;
	}
}
