package org.sid.portfolio.web;

import org.sid.portfolio.dao.CoursRepository;
import org.sid.portfolio.dao.ThematiqueRepository;
import org.sid.portfolio.entities.Contenu;
import org.sid.portfolio.entities.Cours;
import org.sid.portfolio.entities.Image;
import org.sid.portfolio.entities.Utilisateur;
import org.sid.portfolio.entities.Video;
import org.sid.portfolio.services.CoursService;
import org.sid.portfolio.services.ImageStorageService;
import org.sid.portfolio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminCoursController {
	
	@Autowired
	private CoursRepository coursRepository;
	@Autowired
	private ThematiqueRepository thematiqueRepository;
	@Autowired
	private ImageStorageService imageStorageService;
	@Autowired
	private CoursService coursService;
	
	@GetMapping(path="/allCours")
	public String cour(Model model,@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "8") int size,
			@RequestParam(name="keyword",defaultValue = "") String mc,Long totPages) {
		if(page == -1) page++;
		System.out.println(totPages+" "+page);
		if(totPages != null) {
			if(totPages <= page) page--;
			System.out.println(totPages+" "+page);
		}

		Page<Cours> pageCours = coursRepository.findByTitreContains(mc, PageRequest.of(page, size));
		
		if(page < 0 ) page++;
		if(page > pageCours.getTotalPages()) page--;
		
		model.addAttribute("cours", pageCours.getContent());
		model.addAttribute("thematiques", thematiqueRepository.findAll());
		model.addAttribute("totPages",pageCours.getTotalPages());
		model.addAttribute("pages",new int[pageCours.getTotalPages()]);
		model.addAttribute("currentPage2", page);
		model.addAttribute("keyword",mc);
		return "admin/adminCour";
	}
	
	@RequestMapping(path="/saveCours",method = RequestMethod.POST)
	public String saveCours(String titre,Long thematique,String description,@RequestParam("photo") MultipartFile photo) {
		Image img = imageStorageService.saveFile(photo);
		if(img != null) {
			Cours c = new Cours();
			c.setImage(img);
			c.setDescription(description);
			c.setThematique(thematiqueRepository.findById(thematique).get());
			c.setTitre(titre);
			c.setCode(coursService.generateRandomPassword(14));
			coursRepository.save(c);
		}
		return "redirect:/allCours";
	}
	
	@RequestMapping(path="/editCours")
	public String editCours(Model model,Long idC) {
		Cours c = coursRepository.findById(idC).get();
		model.addAttribute("thematiques", thematiqueRepository.findAll());
		model.addAttribute("cours", c);
		return "admin/modifierCour";
	} 
	
	@RequestMapping(path="/updateCours",method = RequestMethod.POST)
	public String updateCours(Long id,String titre,Long thematique,String description,boolean code,@RequestParam("photo") MultipartFile photo) {
		Cours c = coursRepository.findById(id).get();
		if(!photo.isEmpty()) {
			Image img = imageStorageService.saveFile(photo);
			if(img!=null) 
				c.setImage(img);
		}
		c.setDescription(description);
		c.setThematique(thematiqueRepository.findById(thematique).get());
		c.setTitre(titre);
		if(code)
			c.setCode(coursService.generateRandomPassword(14));
		coursRepository.save(c);
		return "redirect:/allCours";
	}
	
	@GetMapping(path="/deleteCours")
	public String deletecours(Long idC) {
		coursRepository.deleteById(idC);
		return "redirect:/allCours";
	}
	
	@GetMapping(path="/coursUsers")
	public String coursUsers(Model model,Long idC,@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "8") int size,
			@RequestParam(name="keyword",defaultValue = "") String mc,Long totPages) {
		Cours c = coursRepository.findById(idC).get();
		if(page == -1) page++;
		System.out.println(totPages+" "+page);
		if(totPages != null) {
			if(totPages <= page) page--;
			System.out.println(totPages+" "+page);
		}

		Page<Utilisateur> pageCours = coursService.getUsersByCoursContainsMc(PageRequest.of(page, size),c,mc);
		
		if(page < 0 ) page++;
		if(page > pageCours.getTotalPages()) page--;
		model.addAttribute("users",pageCours.getContent());
		model.addAttribute("totPages",pageCours.getTotalPages());
		model.addAttribute("pages",new int[pageCours.getTotalPages()]);
		model.addAttribute("currentPage2", page);
		model.addAttribute("keyword",mc);
		model.addAttribute("cours",c);
		return "admin/studentListInCours";
	}
	
	@GetMapping(path="/removeUserFromCours")
	public String removeUserFromCours(Model model,Long idC,Long idU) {
		Cours c = coursRepository.findById(idC).get();
		coursService.removeUserFromCours(idU, idC);
		model.addAttribute("cours",c);
		return "redirect:/coursUsers?idC="+idC;
	}
	
	
}
