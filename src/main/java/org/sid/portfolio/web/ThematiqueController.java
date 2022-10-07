package org.sid.portfolio.web;

import org.sid.portfolio.dao.ThematiqueRepository;
import org.sid.portfolio.entities.Cours;
import org.sid.portfolio.entities.Thematique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ThematiqueController {
	
	@Autowired
	private ThematiqueRepository thematiqueRepository;
	
	@GetMapping(path="/thematiques")
	public String getThematiques(Model model,@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "8") int size,Long totPages) {
		if(page == -1) page++;
		System.out.println(totPages+" "+page);
		if(totPages != null) {
			if(totPages <= page) page--;
			System.out.println(totPages+" "+page);
		}
		Page<Thematique> thPage = thematiqueRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("thematiques", thPage.getContent());
		model.addAttribute("pages",new int[thPage.getTotalPages()]);
		model.addAttribute("totPages",thPage.getTotalPages());
		model.addAttribute("currentPage2", page);
		return "admin/thematique";
	}
	
	@GetMapping(path="/deleteThematique")
	public String deleteThematique(Long idTh) {
		thematiqueRepository.deleteById(idTh);
		//System.out.println(idTh);
		return "redirect:/thematiques";
	}
	
	@PostMapping(path="/saveThematique")
	public String saveThematique(Thematique thematique) {
		thematiqueRepository.save(thematique);
		return "redirect:/thematiques";
	}
}
