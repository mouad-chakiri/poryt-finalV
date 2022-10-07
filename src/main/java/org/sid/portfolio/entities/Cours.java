package org.sid.portfolio.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cours {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titre;
	private String description;
	private String code;
	private Date created = new Date();
	@OneToMany(mappedBy = "cours",cascade=CascadeType.REMOVE)
	private List<Chapitre> chapitres;
	@ManyToMany
	//  @JoinTable(name="USERS_COURS") 
	private List<Utilisateur> utilisateurs;
	
	//@ManyToMany
	@ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
	private List<Utilisateur> demandeurs;
	
	@ManyToOne
	private Thematique thematique;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name="image_id")
	private Image image;

	public Cours() {
		super();
	}
	
	public Cours(String description, String code, List<Chapitre> chapitres, List<Utilisateur> utilisateurs,
			Thematique thematique) {
		super();
		this.description = description;
		this.code = code;
		this.chapitres = chapitres;
		this.utilisateurs = utilisateurs;
		this.thematique = thematique;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Chapitre> getChapitres() {
		return chapitres;
	}

	public void setChapitres(List<Chapitre> chapitres) {
		this.chapitres = chapitres;
	}

	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

	public Thematique getThematique() {
		return thematique;
	}

	public void setThematique(Thematique thematique) {
		this.thematique = thematique;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public List<Utilisateur> getDemandeurs() {
		return demandeurs;
	}

	public void setDemandeurs(List<Utilisateur> demandeurs) {
		this.demandeurs = demandeurs;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	public String getSomeDescription() {
		if(this.description.length()>77)
			return this.description.substring(0,77) + "...";
		else
			return this.description+ "...";
	}
	
	
}
