package org.sid.portfolio.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.sid.portfolio.dao.UtilisateurRepository;
import org.sid.portfolio.entities.Utilisateur;
import org.sid.portfolio.services.CoursService;
import org.sid.portfolio.services.EmailSenderService;
import org.sid.portfolio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired
	private CoursService coursService;
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) throws ServletException {
		request.logout();
		return "redirect:/index";
	}
	@GetMapping(path="/login")
	public String logIn() {
		return "logIn.html";
	}
	
	@GetMapping(path="/creatAccount")
	public String creatAccount() {
		return "creatAccount.html";
	}
	
	@PostMapping("/registration")
	public String registration(Model model,String nom,String prenom,String email,String password) {
		Utilisateur u = new Utilisateur();
		u.setNom(nom);
		u.setPrenom(prenom);
		u.setEmail(email);
		u.setPassword(password);
		boolean success = userService.registration(u);
		if(success) {
			return "redirect:login";
		}
		model.addAttribute("err", true);
		return "creatAccount.html";
	}
	
	@GetMapping(path="/userProfile")
	public String userProfile(Model model) {
		model.addAttribute("user",userService.getAutheenticatedUser());
		return "cour/userProfil.html";
	}
	
	@GetMapping(path="/successAuth")
	public String afterAuth() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur u = utilisateurRepository.findByEmail(auth.getName());
		if(u.getRole().equals("ADMIN")) {
			return "redirect:/admin";
		}else {
			return "redirect:/cours";
		}
	}
	
	@GetMapping(value = "/showReset")
	public String showReset() {
		return "resetPassword";
	}
	
	@PostMapping(value = "/reset")
	public String reset(Model model,String email) {
		Utilisateur u = utilisateurRepository.findByEmail(email);
		if(u==null) {
			model.addAttribute("error", true);
			return "resetPassword";
		}
		else {
			model.addAttribute("user",u);
			String rnd= coursService.generateRandomPassword(20);
			u.setResetToken(rnd);
			utilisateurRepository.save(u);
			emailSenderService.sendSimpleEmail(email, "le code de verification est "+rnd, "code verification");
		}
		return "codeVerification";
	}
	
	@PostMapping(value = "/tokenPage")
	public String tokenPage(Model model,Long id,String code) {
		Utilisateur u = utilisateurRepository.findById(id).get();
		model.addAttribute("user", u);
		if(u.getResetToken().equals(code)) {
			
			return "newPassword";
		}
		model.addAttribute("error", true);
		return "codeVerification";
	}
	
	@PostMapping(value = "/newPassword")
	public String newPassword(Model model,Long id,String newPassword) {
		Utilisateur u = utilisateurRepository.findById(id).get();
		model.addAttribute("user", u);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		u.setPassword(passwordEncoder.encode(newPassword));
		utilisateurRepository.save(u);
		return "redirect:/login";
	}
}
