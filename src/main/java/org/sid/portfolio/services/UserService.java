package org.sid.portfolio.services;

import org.sid.portfolio.dao.UtilisateurRepository;
import org.sid.portfolio.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	public boolean registration(Utilisateur u) {
		Utilisateur usr = utilisateurRepository.findByEmail(u.getEmail());
		//System.out.println(usr.getNom());
		if(usr == null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			u.setPassword(passwordEncoder.encode(u.getPassword()));
			u.setRole("USER");
			utilisateurRepository.save(u);
			return true;
		}
		return false;
	}
	
	public Utilisateur getAutheenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur u = utilisateurRepository.findByEmail(auth.getName());
		return u;
	}
	
	public void disableAccount(Long idU) {
		Utilisateur u = utilisateurRepository.findById(idU).get();
		u.setActive(false);
		utilisateurRepository.save(u);
	}
	
	public void enableAccount(Long idU) {
		Utilisateur u = utilisateurRepository.findById(idU).get();
		u.setActive(true);
		utilisateurRepository.save(u);
	}
	
	public int numberOfUser() {
		int nb = 0;
		for(Utilisateur u : utilisateurRepository.findAll()) {
			if(u.getRole().equals("USER")) nb++;
		}
		return nb;
	}
}
