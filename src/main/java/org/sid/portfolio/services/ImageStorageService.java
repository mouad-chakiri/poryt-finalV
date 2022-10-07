package org.sid.portfolio.services;

import java.io.IOException;
import java.util.Optional;

import org.sid.portfolio.dao.ImageReposirory;
import org.sid.portfolio.entities.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageStorageService {
	
	@Autowired
	private ImageReposirory imageReposirory;
	
	public Image saveFile(MultipartFile file) {
		String imgName = file.getOriginalFilename();
		try {
			Image image = new Image(imgName,file.getContentType(),file.getBytes());
			return imageReposirory.save(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Optional<Image> getFile(Long fileId) {
		return imageReposirory.findById(fileId);
	}
}
