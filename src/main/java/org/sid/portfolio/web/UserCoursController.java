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
import org.sid.portfolio.entities.Image;
import org.sid.portfolio.entities.Thematique;
import org.sid.portfolio.entities.Video;
import org.sid.portfolio.services.CoursService;
import org.sid.portfolio.services.FileStorageService;
import org.sid.portfolio.services.ImageStorageService;
import org.sid.portfolio.services.ThematiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserCoursController {
	
	@Autowired
	private ThematiqueService thematiqueService;
	@Autowired
	private CoursRepository coursRepository;
	@Autowired
	private ImageStorageService imageStorageService;
	@Autowired
	private CoursService coursService;
	@Autowired
	private ChapitreRepository chapitreRepository;
	@Autowired
	private ContenuRepository contenuRepository;
	@Autowired
	private FileStorageService fileStorageService;
	
	@GetMapping(path="/cours")
	public String cour(Model model,@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "6") int size,Long totPages,Long idT) {
		System.out.println(idT);
		if(page == -1) page++;
		System.out.println(totPages+" "+page);
		if(totPages != null) {
			if(totPages <= page) page--;
			System.out.println(totPages+" "+page);
		}
		List<Thematique> thematique6 = new ArrayList<Thematique>();
		List<Thematique> thematiques = new ArrayList<Thematique>();
		List<Thematique> all = thematiqueService.getAllThematiques();
		for(int i=0;i<all.size();i++) {
			if(i<6) thematique6.add(all.get(i));
			else thematiques.add(all.get(i));
		}
		Page<Cours> pageCours = coursRepository.findAll(PageRequest.of(page, size));
		if(idT != null) {
			pageCours = coursRepository.listCoursByCategoriyId(idT, PageRequest.of(page, size));
		}
		
		if(page < 0 ) page++;
		if(page > pageCours.getTotalPages()) page--;
		model.addAttribute("thematiques6", thematique6);
		model.addAttribute("thematiques", thematiques);
		model.addAttribute("cours", pageCours.getContent());
		model.addAttribute("totPages",pageCours.getTotalPages());
		model.addAttribute("pages",new int[pageCours.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("idT", idT);
		return "cour/main.html";
	}
	
	@GetMapping(path="/coursDetails/{id}")
	public String coursDetails(@PathVariable Long id ,Model model,Long dem,
			@RequestParam(name="code",defaultValue = "") String cd) {
		Cours c = coursRepository.findById(id).get();
		model.addAttribute("invalid",false);
		if(!cd.equals("")) {
			boolean access = coursService.accessViaCode(c, cd);
			if(access) {
				return "redirect:/userCours";
			}else {
				model.addAttribute("invalid",true);
			}
		}
		
		if(dem != null) {
			if(dem == 1) {
				coursService.sentAccessRequest(c);
				return "redirect:/cours";
			}
		}
		
		model.addAttribute("cours", c);
		//System.out.println(coursService.hasAccess(c));
		model.addAttribute("hasAccess",coursService.hasAccess(c));
		//System.out.println(coursService.AlreadySent(c));
		model.addAttribute("alreadySent",coursService.AlreadySent(c));
		return "cour/courDetails.html";
	}
	
	@GetMapping(path="/coursContent/{idCr}/{idChp}")
	public String coursContent(Model model,@PathVariable Long idCr,@PathVariable Long idChp,Long num,Long signe) {
		Cours c = coursRepository.findById(idCr).get();
		Chapitre chap=null;
		if(idChp != 0) {
			chap=chapitreRepository.findById(idChp).get();
		}
		if(num != null) {
			int indice=0; 
			List<Chapitre> chapitres = chapitreRepository.getchapitreByCoursId(idCr);
			for(int i=0;i<chapitres.size();i++) {
				if(chapitres.get(i).getNumero() == num) {
					indice = i;
				}
			}
			if(signe == 1) {
				if(indice < chapitres.size()-1) {
					chap = chapitres.get(indice+1);
					return "redirect:/coursContent/"+idCr+"/"+chap.getId();
				}
			}else if(signe == -1) {
				if(indice > 0) {
					chap = chapitres.get(indice-1);
					return "redirect:/coursContent/"+idCr+"/"+chap.getId();
				}
			}
		}else {
			if(idChp == 0) {
				chap = c.getChapitres().get(0);
			}else {
				chap = chapitreRepository.findById(idChp).get();
			}
		}
		List<Contenu> lc = contenuRepository.getContenuByChapterId(chap.getId());
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
		model.addAttribute("chapitres", c.getChapitres());
		model.addAttribute("chapitre", chap);
		model.addAttribute("videos", vc);
		model.addAttribute("documents", dc);
		model.addAttribute("cours", c);
		return "cour/courContent.html";
	}
	

	
	@GetMapping(path="/userCours")
	public String userCours(Model model,@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "1") int size,
			@RequestParam(name="keyword",defaultValue = "") String mc,Long totPages) {
		if(page == -1) page++;
		System.out.println(totPages+" "+page);
		if(totPages != null) {
			if(totPages <= page) page--;
			System.out.println(totPages+" "+page);
		}
		
		Page<Cours> coPage = coursService.getMycoursesContainsKeyword(PageRequest.of(page, size),mc);
		model.addAttribute("myCourses", coPage.getContent());
		model.addAttribute("pages",new int[coPage.getTotalPages()]);
		model.addAttribute("totPages",coPage.getTotalPages());
		model.addAttribute("keyword", mc);
		model.addAttribute("currentPage", page);
		return "cour/userCours.html";
	}
	
	@GetMapping("/getImage/{fileId}")
	public ResponseEntity<ByteArrayResource> getimage(@PathVariable Long fileId){
		Image img = imageStorageService.getFile(fileId).get();
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(img.getTypePic()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachement:filename=\""+img.getNamePic()+"\"")
				.body(new ByteArrayResource(img.getContenu()));
	}
	
	@GetMapping("/getFile/{fileId}")
	public ResponseEntity<ByteArrayResource> getDocument(@PathVariable Long fileId){
		Document doc = (Document)fileStorageService.getFile(fileId).get();
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(doc.getTypeDoc()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachement:filename=\""+doc.getNomDoc()+"\"")
				.body(new ByteArrayResource(doc.getData()));
	}
}
