package org.springframework.samples.petclinic.repository;

import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Pet;

public interface AdoptionRepository extends CrudRepository<Adoption, Integer>{
	
	@Query("SELECT pet FROM Pet pet")
	public Set<Pet> findAllPets() throws DataAccessException;;

}
