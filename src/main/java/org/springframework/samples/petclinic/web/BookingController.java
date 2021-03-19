
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BookingController {

	private final BookingService	bookingService;
	private final UserService		userService;
	private final OwnerService	ownerService;


	@Autowired
	public BookingController(final BookingService bookingService, final UserService userService, final OwnerService ownerService) {
		super();
		this.bookingService = bookingService;
		this.userService = userService;
		this.ownerService = ownerService;
	}

	@GetMapping("/petHotel")
	public ModelAndView getMyBookings() {
		final ModelAndView mav = new ModelAndView("petHotel/myBookings");
		final String username = this.userService.getUserSession().getUsername();
		final Owner owner = this.ownerService.findOwnerByUser(username);
		mav.addObject("myBookings", this.bookingService.getOwnersBookings(owner));
		return mav;
	}
}
