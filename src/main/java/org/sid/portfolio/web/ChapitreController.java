
package org.sid.portfolio.web;

import java.util.ArrayList;
import java.util.List;

import org.sid.portfolio.dao.ChapitreRepository;
import org.sid.portfolio.dao.ContenuRepository;
import org.sid.portfolio.dao.CoursRepository;
import org.sid.portfolio.entities.Chapitre;
import org.sid.portfolio.entities.Contenu;
import org.sid.portfolio.entities.Cours;
import org.sid.portfolio.entities.Document;
import org.sid.portfolio.entities.Video;
import org.sid.portfolio.services.CoursService;
import org.sid.portfolio.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ChapitreController {
	
	@Autowired
	private CoursRepository coursRepository;
	@Autowired
	private ChapitreRepository chapitreRepository;
	@Autowired
	private ContenuRepository contenuRepository;
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private CoursService coursService;

	@GetMapping(path="/chapitres/{idC}")
	public String coursContent(Model model,@PathVariable Long idC) {
		Cours c = coursRepository.findById(idC).get();
		List<Chapitre> chapitres = chapitreRepository.getchapitreByCoursId(idC);
		model.addAttribute("cours", c);
		model.addAttribute("chapitres", chapitres);
		return "admin/chapters";
	}
	
	@PostMapping(path="/saveChapitre")
	public String coursContent(Long idC,int numero,String titre,String description,String links,
			@RequestParam("files") MultipartFile[] files) {
		Cours c = coursRepository.findById(idC).get();
		Chapitre chapitre = new Chapitre();
		chapitre.setCours(c);
		chapitre.setNumero(numero);
		chapitre.setTitle(titre);
		chapitre.setDescription(description);
		Chapitre cp = chapitreRepository.save(chapitre);
		if(!links.isEmpty()) {
			String[] youtubeLinks = links.split(";");
			for(String link : youtubeLinks) {
				Video vid = new Video();
				vid.setUrl(link);
				vid.setChapitre(chapitre);
				Contenu cont = contenuRepository.save(vid);
				cp.getContenus().add(cont);
				chapitreRepository.save(cp);
			}
		}
		for(MultipartFile file : files) {
			Contenu cont = fileStorageService.saveFile(file,cp);
			cp.getContenus().add(cont);
			chapitreRepository.save(cp);
		}
		return "redirect:/chapitres/"+idC;
	}
	
	@GetMapping(path="/deleteChapitre")
	public String deleteChapitre(Long idC,Long idChap) {
		chapitreRepository.deleteById(idChap);
		return "redirect:/chapitres/"+idC;
	}

	@RequestMapping(value = "/editChapitre")
	public String editChapitre(Model model,Long idChp) {
		Chapitre chapitre = chapitreRepository.findById(idChp).get();
		List<Contenu> lc = contenuRepository.getContenuByChapterId(idChp);
		List<Video> vc = new ArrayList<Video>();
		List<Document> dc = new ArrayList<Document>();
		for(Contenu con : lc) {
			if(con instanceof Video) {
				vc.add((Video)con);
			}
			if(con instanceof Document) {
				dc.add((Document)con);
			}
		}
		model.addAttribute("chapitre", chapitre);
		model.addAttribute("videos", vc);
		model.addAttribute("documents", dc);
		return "admin/chapterDetails";
	}
	
	@PostMapping(path="/updateChapitre")
	public String updateChapitre(Long id,int numero,String titre,String description) {
		Chapitre chapitre = chapitreRepository.findById(id).get();
		chapitre.setNumero(numero);
		chapitre.setTitle(titre);
		chapitre.setDescription(description);
		chapitreRepository.save(chapitre);
		return "redirect:/chapitres/"+chapitre.getCours().getId();
	}
	
	@GetMapping(path="/deleteContent")
	public String deleteContent(Long idCon,Long idCh) {
		contenuRepository.deleteById(idCon);
		return "redirect:/editChapitre?idChp="+idCh;
	}
	
	@PostMapping(path="/addContent")
	public String addContent(Long id,String links,
			@RequestParam("files") MultipartFile[] files) {
		Chapitre chapitre = chapitreRepository.findById(id).get();
		if(!links.isEmpty()) {
			String[] youtubeLinks = links.split(";");
			for(String link : youtubeLinks) {
				Video vid = new Video();
				vid.setUrl(link);
				vid.setChapitre(chapitre);
				Contenu cont = contenuRepository.save(vid);
				chapitre.getContenus().add(cont);
				chapitreRepository.save(chapitre);
			}
		}
		for(MultipartFile file : files) {
			if(!file.isEmpty()) {
				Contenu cont = fileStorageService.saveFile(file,chapitre);
				chapitre.getContenus().add(cont);
				chapitreRepository.save(chapitre);
			}
		}
		return "redirect:/editChapitre?idChp="+chapitre.getCours().getId();//err
	}
	
	@PostMapping(path="/addUsersToCours")
	public String addUsersToCours(Long idC,String emails) {
		//Cours c = coursRepository.findById(idC).get();
		if(!emails.isEmpty()) {
			String[] emailsUs = emails.split(";");
			for(String m : emailsUs) {
				coursService.addUserToCours(m, idC);
			}
		}
		return "redirect:/chapitres/"+idC;
	}
	
}
