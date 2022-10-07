package org.sid.portfolio.dao;

import java.util.List;


import org.sid.portfolio.entities.Contenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ContenuRepository extends JpaRepository<Contenu, Long>{
	@Query("select c from Contenu c where c.chapitre.id = :x")
	public List<Contenu> getContenuByChapterId(@Param("x") Long chapId);
}
