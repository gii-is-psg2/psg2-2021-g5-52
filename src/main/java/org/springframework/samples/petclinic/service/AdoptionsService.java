package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoptions;
import org.springframework.samples.petclinic.repository.AdoptionsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionsService {

	private final AdoptionsRepository adoptionsRepository;
	
	@Autowired
	public AdoptionsService(final AdoptionsRepository adoptionsRepository) {
		super();
		this.adoptionsRepository = adoptionsRepository;
	}
	
	@Transactional
	public void saveAdoptions(final Adoptions adoptions) {
		this.adoptionsRepository.save(adoptions);
	}
	
	@Transactional
	public void deleteAdoptions(final Adoptions adoptions) {
		this.adoptionsRepository.delete(adoptions);
	}
	
	@Transactional
	public List<Adoptions> findAll() {
		return this.adoptionsRepository.findAll;
	}

	@Transactional
	public void deleteAdoptionsById(final int adoptionsId) {
		this.adoptionsRepository.deleteById(adoptionsId);
		
	}
	
	@Transactional(readOnly = true)
	public Optional<Adoptions> getAdoptionsById(final int adoptionsId) {
		return this.adoptionsRepository.findById(adoptionsId);
	}

}
