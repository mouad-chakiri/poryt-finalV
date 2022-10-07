package org.sid.portfolio.entities;

public class DemandeDTO {
	private Utilisateur demandeur;
	private Cours cours;
	public Utilisateur getDemandeur() {
		return demandeur;
	}
	public void setDemandeur(Utilisateur demandeur) {
		this.demandeur = demandeur;
	}
	public Cours getCours() {
		return cours;
	}
	public void setCours(Cours cous) {
		this.cours = cous;
	}
	
}
