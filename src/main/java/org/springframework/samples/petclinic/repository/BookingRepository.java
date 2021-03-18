
package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.PetType;


public interface BookingRepository extends Repository<Booking, Integer> {
	
	Booking findById(int id) throws DataAccessException;

	
	void save(Booking booking) throws DataAccessException;
}