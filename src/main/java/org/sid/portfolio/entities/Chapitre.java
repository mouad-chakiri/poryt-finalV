package org.sid.portfolio.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Chapitre {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private int numero;
	private String description;
	@ManyToOne
	private Cours cours;
	 
	@OneToMany(mappedBy = "chapitre",cascade=CascadeType.REMOVE)
	private List<Contenu> contenus;
	
	
	public Chapitre(String title, int numero, Cours cours, List<Contenu> contenus) {
		super();
		this.title = title;
		this.numero = numero;
		this.cours = cours;
		this.contenus = contenus;
	}

	public Chapitre() {
		contenus = new ArrayList<Contenu>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Cours getCours() {
		return cours;
	}

	public void setCours(Cours cours) {
		this.cours = cours;
	}

	public List<Contenu> getContenus() {
		return contenus;
	}

	public void setContenus(List<Contenu> contenus) {
		this.contenus = contenus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
	
}
