package org.sid.portfolio.dao;

import org.sid.portfolio.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
	public Utilisateur findByEmail(String email);
}
