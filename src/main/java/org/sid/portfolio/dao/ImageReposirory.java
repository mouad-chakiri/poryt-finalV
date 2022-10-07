package org.sid.portfolio.dao;

import org.sid.portfolio.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository 
public interface ImageReposirory extends JpaRepository<Image, Long>{
	
}
