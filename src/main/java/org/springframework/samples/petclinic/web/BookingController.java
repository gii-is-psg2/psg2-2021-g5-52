
package org.springframework.samples.petclinic.web;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookingController {

	private final BookingService	bookingService;
	private final UserService		userService;
	private final PetService		petService;


	@Autowired
	public BookingController(final BookingService bookingService, final UserService userService, final PetService petService) {
		super();
		this.bookingService = bookingService;
		this.userService = userService;
		this.petService = petService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("booking")
	public Booking loadPetWithBooking(@PathVariable("petId") final int petId) {
		final Pet pet = this.petService.findPetById(petId);
		final Booking booking = new Booking();
		pet.addBooking(booking);
		return booking;
	}

	// Spring MVC calls method loadPetWithBooking(...) before initNewBookingForm is called
	@GetMapping(value = "/owners/*/pets/{petId}/bookings/new")
	public String initNewBookingForm(@PathVariable("petId") final int petId, final Map<String, Object> model) {
		return "pets/createOrUpdateBookingForm";
	}

	// Spring MVC calls method loadPetWithBooking(...) before processNewBookingForm is called
	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/bookings/new")
	public String processNewBookingForm(@Valid final Booking booking, final BindingResult result) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateBookingForm";
		} else {
			this.petService.saveBooking(booking);
			return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping(value = "/owners/*/pets/{petId}/bookings")
	public String showBookings(@PathVariable final int petId, final Map<String, Object> model) {
		model.put("bookings", this.petService.findPetById(petId).getBookings());
		return "bookingList";
	}

	@GetMapping("/owners/{ownerId}/pets/{petId}/bookings/{bookingId}/delete")
	public String cancelBooking(@PathVariable("ownerId") final int ownerId, @PathVariable("petId") final int petId, @PathVariable("bookingId") final int bookingId) {
		final String username = this.userService.getUserSession().getUsername();
		final Optional<Booking> optionalBooking = this.bookingService.getBookingById(bookingId);
		// Checking user who cancels the booking is the user who made it
		if (optionalBooking.isPresent() && username.equals(optionalBooking.get().getPet().getOwner().getUser().getUsername()))
			this.bookingService.deleteBookingById(bookingId);
		return "redirect:/owners/" + ownerId;
	}
}
