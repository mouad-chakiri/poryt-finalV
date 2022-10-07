package org.sid.portfolio.services;

import java.io.IOException;
import java.util.Optional;

import org.sid.portfolio.dao.ContenuRepository;
import org.sid.portfolio.entities.Chapitre;
import org.sid.portfolio.entities.Contenu;
import org.sid.portfolio.entities.Document;
import org.sid.portfolio.entities.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
	
	@Autowired
	private ContenuRepository contenuRepository;
	
	public Contenu saveFile(MultipartFile file,Chapitre ch) {
		String fileName = file.getOriginalFilename();
		try {
			Document doc = new Document(fileName,file.getContentType(),file.getBytes());
			doc.setChapitre(ch);
			return contenuRepository.save(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Optional<Contenu> getFile(Long fileId) {
		return contenuRepository.findById(fileId);
	}
}
