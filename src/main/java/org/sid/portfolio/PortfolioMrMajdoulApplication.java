package org.sid.portfolio;

import org.sid.portfolio.dao.ContenuRepository;
import org.sid.portfolio.dao.CoursRepository;
import org.sid.portfolio.dao.UtilisateurRepository;
import org.sid.portfolio.entities.Contenu;
import org.sid.portfolio.entities.Cours;
import org.sid.portfolio.entities.Document;
import org.sid.portfolio.entities.Utilisateur;
import org.sid.portfolio.entities.Video;
import org.sid.portfolio.services.CoursService;
import org.sid.portfolio.services.EmailSenderService;
import org.sid.portfolio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import groovy.transform.Trait;

@SpringBootApplication
public class PortfolioMrMajdoulApplication implements CommandLineRunner{
	@Autowired
	private UserService userServices;
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@Autowired
	private CoursRepository coursRepository;
	@Autowired
	private CoursService coursService;
	@Autowired
	private ContenuRepository contenuRepository;
	@Autowired
	private EmailSenderService emailSenderService;
	
	public static void main(String[] args) {
		SpringApplication.run(PortfolioMrMajdoulApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	/*	Cours c = coursRepository.findById(1L).get();
		utilisateurRepository.findByEmail("freeman04720@gmail.com").
				getMyCourses().forEach(cr->{
					if(c.getId() == cr.getId()) {
						System.out.println(true);
					}
				});*/
/*		utilisateurRepository.findByEmail("freeman04720@gmail.com").
		getPendingCourses().forEach(pc->{
			System.out.println(pc.getTitre()); 
		});*/
	/*	Utilisateur u = new Utilisateur();
		u.setActive(true);
		u.setEmail("a.o@gmail.com");
		u.setPassword("1234");
		userServices.registration(u);*/
	/*	coursRepository.findByTitreContains("").forEach(c->{
			System.out.println(c.getTitre());
		});
		Cours c = coursRepository.findById(5L).get();
		Utilisateur u = utilisateurRepository.findByEmail("freeman04720@gmail.com");
		u.getPendingCourses().add(c);
		c.getDemandeurs().add(u);
		
		utilisateurRepository.save(u); */
		/*contenuRepository.getContenuByChapterId(2L).forEach(c->{
			if(c instanceof Document) {
				System.out.println(((Document)c).getNomDoc());
			}
			if(c instanceof Video) {
				System.out.println(((Video)c).getUrl());
			}
		});
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		utilisateurRepository.findAll().forEach(u->{
			System.out.println(passwordEncoder.matches("1234", u.getPassword()));
		});
		emailSenderService.sendSimpleEmail("freeman04720@gmail.com",
				"This is Email Body", "this is subject");*/
	}

}
