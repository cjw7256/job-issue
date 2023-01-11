package sample.project.jobissue.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFile {
	private String originalFileName; // 사용자가 업로드한 파일명
	private String realFileName; // 실제 저장된 파일명
}
