package org.sid.portfolio.web;

import org.sid.portfolio.dao.CoursRepository;
import org.sid.portfolio.dao.ThematiqueRepository;
import org.sid.portfolio.services.CoursService;
import org.sid.portfolio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemandeController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private CoursRepository coursRepository;
	@Autowired
	private ThematiqueRepository thematiqueRepository;
	@Autowired
	private CoursService coursService;
	
	@RequestMapping(value = "/admin")
	public String adminMainPage(Model model) {
		model.addAttribute("nbUsers", userService.numberOfUser());
		model.addAttribute("nbCourses", coursRepository.findAll().size());
		model.addAttribute("nbThematiques", thematiqueRepository.findAll().size());
		model.addAttribute("nbDem", coursService.numberOfDemandes());
		model.addAttribute("demandes", coursService.getDemandes());
		return "admin/index";
	}
	
	
	@RequestMapping(value = "/accepter")
	public String accepterDemande(Long idU,Long idC) {
		coursService.accepterDemande(idU, idC);
		return "redirect:/admin";
	}
	
	@RequestMapping(value = "/refuser")
	public String refuserDemande(Long idU,Long idC) {
		coursService.refuserDemande(idU, idC);
		return "redirect:/admin";
	}
}
