package org.sid.portfolio.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Thematique {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomThematique;
	
	@OneToMany(mappedBy = "thematique",cascade=CascadeType.REMOVE)
	private List<Cours> cours;
	
	public Thematique(String nomThematique) {
		this.nomThematique = nomThematique;
	}
	
	public Thematique() { }
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomThematique() {
		return nomThematique;
	}
	public void setNomThematique(String nomThematique) {
		this.nomThematique = nomThematique;
	}
	
	
}
