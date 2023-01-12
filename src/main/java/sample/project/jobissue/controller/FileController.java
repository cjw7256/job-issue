package sample.project.jobissue.controller;

import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import sample.project.jobissue.file.FileStoreManager;

@Controller
@RequiredArgsConstructor
public class FileController {
	
	private final FileStoreManager fileStoreManager;
	
	@ResponseBody
	@GetMapping(value="/file/images/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public Resource fileImage(@PathVariable String fileName) throws MalformedURLException {
		return new UrlResource("file:" + fileStoreManager.getFileFullPath(fileName));
	}
	
}
