package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Application;

public interface ApplicationRepository extends CrudRepository<Application, Integer>{
	
	@Query("SELECT a FROM Application a where a.closed = false and a.pet.owner.user.username = ?1")
    List<Application> findAplicationsToMeNotClosed(String username) throws DataAccessException;

}
