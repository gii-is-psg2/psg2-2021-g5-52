package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookingController {
	
	private final BookingService bookingService;
	private final UserService userService;
	private final OwnerService ownerService;
	
	@Autowired
	public BookingController(final BookingService bookingService, final UserService userService, final OwnerService ownerService) {
		super();
		this.bookingService = bookingService;
		this.userService = userService;
		this.ownerService = ownerService;
	}
	
	@GetMapping("petHotel/{ownerId}")
	public ModelAndView getMyBookings(@PathVariable("ownerId") final int ownerId) {
		final ModelAndView mav = new ModelAndView();
		final User user = this.userService.getUserSession();
		final Owner owner = this.ownerService.findOwnerById(ownerId);
		if (!user.equals(owner.getUser())) {
			mav.setViewName("welcome");
		} else {
			mav.setViewName("/petHotel/myBookings");
			mav.addObject("myBookings", this.bookingService.getOwnersBookings(ownerId));
		}
		return mav;
	}

}
