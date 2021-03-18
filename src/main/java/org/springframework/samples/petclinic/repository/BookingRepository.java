package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.model.Booking;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;


public class BookingRepository extends CrudRepository<Booking, Integer>{
    
    Booking findById(int id) throws DataAccessException;

	void save(Booking booking) throws DataAccessException;
}
