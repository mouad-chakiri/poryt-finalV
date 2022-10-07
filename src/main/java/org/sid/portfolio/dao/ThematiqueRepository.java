package org.sid.portfolio.dao;

import org.sid.portfolio.entities.Thematique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ThematiqueRepository extends JpaRepository<Thematique, Long>{

}
