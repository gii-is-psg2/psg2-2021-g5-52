package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Owner;


public interface BookingRepository extends CrudRepository<Booking, Integer>{

	@Query("SELECT b FROM Booking b WHERE b.pet.owner = :owner")
	List<Booking> getOwnersBookings(@Param("owner") Owner owner);
   
}
