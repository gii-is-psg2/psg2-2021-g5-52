package org.springframework.samples.petclinic.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionService {

	private final AdoptionRepository adoptionsRepository;
	
	@Autowired
	public AdoptionService(final AdoptionRepository adoptionsRepository) {
		this.adoptionsRepository = adoptionsRepository;
	}
	
	@Transactional
	public void saveAdoptions(final Adoption adoptions) {
		this.adoptionsRepository.save(adoptions);
	}
	
	@Transactional
	public void deleteAdoptions(final Adoption adoptions) {
		this.adoptionsRepository.delete(adoptions);
	}
	
	@Transactional
	public Optional<Adoption> findById(final int id){
		return this.adoptionsRepository.findById(id);
		
	}
	
	@Transactional(readOnly = true)	
	public Set<Pet> findAllPet() throws DataAccessException {
		return this.adoptionsRepository.findAllPets();
	}
	
	@Transactional
	public Iterable<Adoption> findAll() {
		return this.adoptionsRepository.findAll();
	}

	@Transactional
	public void deleteAdoptionsById(final int adoptionsId) {
		this.adoptionsRepository.deleteById(adoptionsId);
		
	}
	
	@Transactional(readOnly = true)
	public Optional<Adoption> getAdoptionsById(final int adoptionsId) {
		return this.adoptionsRepository.findById(adoptionsId);
	}

}
