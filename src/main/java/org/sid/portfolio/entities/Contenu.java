package org.sid.portfolio.entities;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Type",length = 3)
public abstract class Contenu {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	@ManyToOne
	private Chapitre chapitre;
	
	public Contenu() {
	}

	public Contenu(Chapitre chapitre) {
		super();
		this.chapitre = chapitre;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Chapitre getChapitre() {
		return chapitre;
	}

	public void setChapitre(Chapitre chapitre) {
		this.chapitre = chapitre;
	}
	
	
	
}
