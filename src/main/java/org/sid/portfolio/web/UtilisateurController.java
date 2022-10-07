package org.sid.portfolio.web;

import org.sid.portfolio.dao.UtilisateurRepository;
import org.sid.portfolio.entities.Utilisateur;
import org.sid.portfolio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UtilisateurController {
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private UserService usrService;

	
	@GetMapping(value = "/utilisateurs")
	public String utilisateurs(Model model,@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "8") int size,Long totPages) {
		if(page == -1) page++;
		System.out.println(totPages+" "+page);
		if(totPages != null) {
			if(totPages <= page) page--;
			System.out.println(totPages+" "+page);
		}
		Page<Utilisateur> usPage =  utilisateurRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("users", usPage.getContent());
		model.addAttribute("pages",new int[usPage.getTotalPages()]);
		model.addAttribute("totPages",usPage.getTotalPages());
		model.addAttribute("currentPage2", page);
		return "admin/utilisateur";
	}
	
	@GetMapping(value = "/enableAccount")
	public String enableAccount(Long idC) {
		usrService.enableAccount(idC);
		return "redirect:/utilisateurs";
	}
	
	@GetMapping(value = "/disableAccount")
	public String disableAccount(Long idC) {
		usrService.disableAccount(idC);
		return "redirect:/utilisateurs";
	}
	
	@GetMapping(value = "/deleteAccount")
	public String deleteAccount(Long idC) {
		utilisateurRepository.deleteById(idC);
		return "redirect:/utilisateurs";
	}
	
	@PostMapping(value = "/updateUser")
	public String updateUser(Model model,String prenom,String nom,String oldPassword,String newPassword) {
		model.addAttribute("error", false);
		Utilisateur u = usrService.getAutheenticatedUser();
		model.addAttribute("user",u);
		u.setNom(nom);
		u.setPrenom(prenom);
		if(!oldPassword.isEmpty() && !newPassword.isEmpty()) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if(passwordEncoder.matches(oldPassword, u.getPassword())) {
				u.setPassword(passwordEncoder.encode(newPassword));
		 	}else {
		 		model.addAttribute("error", true);
		 	}
		}
		utilisateurRepository.save(u);
		return "cour/userProfil";
	}
	
	
	
	
}
