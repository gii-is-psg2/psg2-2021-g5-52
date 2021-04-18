package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Adoptions;

public interface AdoptionsRepository extends CrudRepository<Adoptions, Integer>{
	
	

}
