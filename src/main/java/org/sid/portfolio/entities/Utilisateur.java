package org.sid.portfolio.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="users")
public class Utilisateur { 
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String prenom;
	@Column(unique=true)
	private String email;
	private String role;
	private String resetToken;
	private String password;
	@ManyToMany(mappedBy = "utilisateurs", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Cours> MyCourses;
	
/*	@ManyToMany(mappedBy = "demandeurs",fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)*/
	@ManyToMany(fetch = FetchType.EAGER,
    cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    },
    mappedBy = "demandeurs")
	private List<Cours> PendingCourses;
	
	private boolean active = true;
	
	public Utilisateur() {
		super();
	}
	
	public Utilisateur(String nom, String prenom, String email, String password, List<Cours> cours, boolean active) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.MyCourses = cours;
		this.active = active;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	//@JsonIgnore
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Cours> getMyCourses() {
		return MyCourses;
	}

	public void setMyCourses(List<Cours> myCourses) {
		MyCourses = myCourses;
	}

	public List<Cours> getPendingCourses() {
		return PendingCourses;
	}

	public void setPendingCourses(List<Cours> pendingCourses) {
		PendingCourses = pendingCourses;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	
}
