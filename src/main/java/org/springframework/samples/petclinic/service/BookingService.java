
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.service.exceptions.BookingSavingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

	private final BookingRepository bookingRepository;


	@Autowired
	public BookingService(final BookingRepository bookingRepository) {
		super();
		this.bookingRepository = bookingRepository;
	}

	@Transactional
	public void saveBooking(final Booking booking) throws BookingSavingException {
		final List<Booking> oldBookings = this.bookingRepository.getPetsBooking(booking.getPet());

		final LocalDate newStartDate = booking.getStartDate();
		final LocalDate newEndDate = booking.getEndDate();
		final LocalDate today = LocalDate.now();

		if (newStartDate.compareTo(newEndDate) > 0) {
			throw new BookingSavingException("La fecha de fin debe ser posterior a la fecha de inicio");
		}

		else if (newStartDate.compareTo(today) < 0 || newEndDate.compareTo(today) < 0) {
			throw new BookingSavingException("La reserva debe ser para días posteriores al día de hoy");
		}

		for (final Booking old : oldBookings) {
			old.checkConcurrency(newStartDate, newEndDate);
		}

		this.bookingRepository.save(booking);
	}

	@Transactional(readOnly = true)
	public List<Booking> getOwnersBookings(final Owner owner) {
		return this.bookingRepository.getOwnersBookings(owner);
	}

	@Transactional
	public void deleteBooking(final Booking booking) {
		this.bookingRepository.delete(booking);
	}

	@Transactional
	public void deleteBookingById(final int bookingId) {
		this.bookingRepository.deleteById(bookingId);

	}

	@Transactional(readOnly = true)
	public Optional<Booking> getBookingById(final int bookingId) {
		return this.bookingRepository.findById(bookingId);
	}

}
