package org.sid.portfolio.services;

import java.util.List;

import org.sid.portfolio.dao.ThematiqueRepository;
import org.sid.portfolio.entities.Thematique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThematiqueService {
	
	@Autowired
	private ThematiqueRepository thematiqueRepository;
	
	public List<Thematique> getAllThematiques() {
		return thematiqueRepository.findAll();
	}
}
