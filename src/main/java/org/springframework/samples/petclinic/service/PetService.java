/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.service.exceptions.BookingSavingException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PetService {

	private final PetRepository		petRepository;
	private final BookingService	bookingService;
	private final VisitService visitService;


	@Autowired
	public PetService(final PetRepository petRepository, final VisitRepository visitRepository, final BookingService bookingService, final VisitService visitService) {
		this.petRepository = petRepository;
		this.visitService=visitService;
		this.bookingService=bookingService;

	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return this.petRepository.findPetTypes();
	}
	@Transactional
	public void saveVisit(final Visit visit) throws DataAccessException {
		this.visitService.save(visit);
	}
	
	@Transactional
	public void delete(final Pet pet) throws DataAccessException {
		this.petRepository.delete(pet);
	}


	public void deletePetAndVisists(final Pet pet) throws DataAccessException{
		
		for(final Visit v:pet.getVisits()) {
			this.visitService.delete(v);
			
		}
		
		this.petRepository.delete(pet);
		
	}
	
	
	
	@Transactional(readOnly = true)
	public Pet findPetById(final int id) throws DataAccessException {
		return this.petRepository.findById(id);
	}

	@Transactional(rollbackFor = DuplicatedPetNameException.class)
    public void savePet(final Pet pet) throws DataAccessException, DuplicatedPetNameException {
        final Integer id = pet.getId();
        final Pet otherPet = pet.getOwner().getPetwithIdDifferent(pet.getName(), id);
        if (StringUtils.hasLength(pet.getName()) && 
            (otherPet != null && !otherPet.getId().equals(id))) {
            throw new DuplicatedPetNameException();
        } else
            this.petRepository.save(pet);
    }

	public Collection<Visit> findVisitsByPetId(final int petId) {
		return this.visitService.findByPetId(petId);
	}

	@Transactional
	public void saveBooking(final Booking booking) throws BookingSavingException {
		try {
			this.bookingService.saveBooking(booking);
		} catch (Exception e) {
			
		}
	}
	
	@Transactional
	public boolean isOwnerOf(final int petId, final String username) {
		final Pet pet = this.petRepository.findById(petId);
		return pet.getOwner().getUser().getUsername().equals(username);
	}
	
	@Transactional
	public void setInAdoption(final int petId) {
		final Pet pet = this.petRepository.findById(petId);
		pet.setInAdoption(true);
		this.petRepository.save(pet);
	}
	
	@Transactional
	public List<Pet> findPetsForAdoption(String username){
		
		return this.petRepository.findPetsForAdoption(username);
	}
	
	@Transactional
	public List<Pet> findPetsNotAdoptionByUsername(String username){
		
		return this.petRepository.findPetsNotAdoptionByUsername(username);
	}


}