package org.sid.portfolio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.sid.portfolio.dao.CoursRepository;
import org.sid.portfolio.dao.UtilisateurRepository;
import org.sid.portfolio.entities.Cours;
import org.sid.portfolio.entities.DemandeDTO;
import org.sid.portfolio.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CoursService {
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private CoursRepository coursRepository;
	
	public boolean AlreadySent(Cours c) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur u = utilisateurRepository.findByEmail(auth.getName());
		for(Cours cr : u.getPendingCourses()) {
			if(cr.getId() == c.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean AlreadySent(Utilisateur u,Cours c) {
		for(Cours cr : u.getPendingCourses()) {
			if(cr.getId() == c.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasAccess(Cours c) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur u = utilisateurRepository.findByEmail(auth.getName());
		for(Cours crs : u.getMyCourses()) {
			if(crs.getId() == c.getId()) return true;
		}
		return false;
	}
	
	public void sentAccessRequest(Cours c) {
		if(!this.AlreadySent(c)) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Utilisateur u = utilisateurRepository.findByEmail(auth.getName());
			u.getPendingCourses().add(c);
			c.getDemandeurs().add(u);
			utilisateurRepository.save(u);
		}
	}
	
	public boolean accessViaCode(Cours c,String code) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur u = utilisateurRepository.findByEmail(auth.getName());
		if(c.getCode().equals(code)) {
			u.getMyCourses().add(c);
			c.getUtilisateurs().add(u);
			utilisateurRepository.save(u);
			if(this.AlreadySent(c)) {
				u.getPendingCourses().remove(c);
				c.getDemandeurs().remove(u);
				utilisateurRepository.save(u);
			}
			return true;
		}
		return false;
	}
	

	
	public Page<Cours> getMycoursesContainsKeyword(Pageable pageable,String mc) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur u = utilisateurRepository.findByEmail(auth.getName());
		//List<Cours> courses = u.getMyCourses();
		List<Cours> finalList = new ArrayList<Cours>();
		List<Cours> coursesCont = coursRepository.findByTitreContains(mc);
		for(Cours c : u.getMyCourses()) {
			for(Cours c2 :coursesCont) {
				if(c.getId() == c2.getId()) {
					finalList.add(c2);
				}
				System.out.println(c.getTitre()+" "+c2.getTitre());
			}
		}
		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > finalList.size() ? finalList.size() : (start + pageable.getPageSize());
		return new PageImpl<Cours>(finalList.subList(start, end), pageable, finalList.size());
	}
	
	public int numberOfDemandes() {
		int nb = 0;
		List<Cours> courses = coursRepository.findAll();
		for(Cours c : courses) {
			nb += c.getDemandeurs().size();
		}
		return nb;
	}
	
	public List<DemandeDTO> getDemandes(){
		List<DemandeDTO> demandes = new ArrayList<DemandeDTO>();
		for(Utilisateur u : utilisateurRepository.findAll()) {
			if(!u.getPendingCourses().isEmpty()) {
				for(Cours c : u.getPendingCourses()) {
					DemandeDTO dem = new DemandeDTO();
					dem.setCours(c);
					dem.setDemandeur(u);
					demandes.add(dem);
				}
			}
		}
		return demandes;
	}
	
	public void accepterDemande(Long idU,Long idC) {
		Cours c = coursRepository.findById(idC).get();
		Utilisateur u = utilisateurRepository.findById(idU).get();
		u.getPendingCourses().remove(c);
		u.getMyCourses().add(c);
		c.getDemandeurs().remove(u);
		c.getUtilisateurs().add(u);
		utilisateurRepository.save(u);
	}
	
	public void refuserDemande(Long idU,Long idC) {
		Cours c = coursRepository.findById(idC).get();
		Utilisateur u = utilisateurRepository.findById(idU).get();
		u.getPendingCourses().remove(c);
		c.getDemandeurs().remove(u);
		utilisateurRepository.save(u);
	}
	
	public static String generateRandomPassword(int len) {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
          +"lmnopqrstuvwxyz!@#$%&";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}
	
	public Page<Utilisateur> getUsersByCoursContainsMc(Pageable pageable,Cours c,String mc) {
		List<Utilisateur> finalUsers = new ArrayList<Utilisateur>();
		List<Utilisateur> users = c.getUtilisateurs();
		for(Utilisateur u : users) {
			if(u.getNom().contains(mc) || u.getPrenom().contains(mc)) finalUsers.add(u);
		}
		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > finalUsers.size() ? finalUsers.size() : (start + pageable.getPageSize());
		return new PageImpl<Utilisateur>(finalUsers.subList(start, end), pageable, finalUsers.size());
	}
	
	public void removeUserFromCours(Long idU,Long idC) {
		Cours c = coursRepository.findById(idC).get();
		Utilisateur u = utilisateurRepository.findById(idU).get();
		u.getMyCourses().remove(c);
		c.getUtilisateurs().remove(u);
		utilisateurRepository.save(u);
	}
	
	public void addUserToCours(String email,Long idC) {
		Cours c = coursRepository.findById(idC).get();
		Utilisateur u = utilisateurRepository.findByEmail(email);
		if(this.AlreadySent(u,c)) {
			u.getPendingCourses().remove(c);
			c.getDemandeurs().remove(u);
			utilisateurRepository.save(u);
		}
		u.getMyCourses().add(c);
		c.getUtilisateurs().add(u);
		utilisateurRepository.save(u);
	}
}
