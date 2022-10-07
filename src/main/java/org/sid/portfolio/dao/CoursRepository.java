package org.sid.portfolio.dao;

import java.util.List;

import org.sid.portfolio.entities.Cours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CoursRepository extends JpaRepository<Cours, Long>{
	@Query("select c from Cours c where c.thematique.id=:x")
	public Page<Cours> listCoursByCategoriyId(@Param("x") Long idCat,Pageable pageable);
	public Page<Cours> findByTitreContains(String mc,Pageable pageable);
	public List<Cours> findByTitreContains(String mc);
}
