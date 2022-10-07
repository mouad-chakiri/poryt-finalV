package org.sid.portfolio.dao;

import java.util.List;

import org.sid.portfolio.entities.Chapitre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ChapitreRepository extends JpaRepository<Chapitre, Long>{
	@Query("select c from Chapitre c where c.cours.id = :x")
	public List<Chapitre> getchapitreByCoursId(@Param("x") Long coursId);
}
