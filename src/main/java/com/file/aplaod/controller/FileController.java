package com.file.aplaod.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.file.aplaod.payloads.FileResponse;
import com.file.aplaod.service.FileService;



@RestController
@RequestMapping("/file")
public class FileController {
	@Autowired
	private FileService fileService;
	@Value("${projet.image}")
	private String path;

	@PostMapping("/upload")
	public ResponseEntity<FileResponse> uploadImage(@RequestParam("image") MultipartFile image) {
		String fileName = this.fileService.uplaodImage(path, image);
		return new ResponseEntity<FileResponse>(new FileResponse(fileName, "image is successfully uploaded"),
				HttpStatus.OK);
	}

	// method to server image
	@GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
