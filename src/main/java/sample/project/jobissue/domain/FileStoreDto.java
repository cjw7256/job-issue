package sample.project.jobissue.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileStoreDto {
	private String filename;
	private String uploadFilename;
	private String filepath;
	private String filetype;
	private String tableCode;
	private String pkId;
}
