package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.samples.petclinic.service.exceptions.ClosedCauseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class DonationController {

	private final DonationService donationService;
	private final CauseService causeService;
	private final CauseController causeController;
	
	
	@Autowired
	public DonationController(DonationService donationService,CauseService causeService,CauseController causeController) {
		this.donationService = donationService;
		this.causeService=causeService;
		this.causeController=causeController;
	}

	
	@GetMapping(value = { "/donations/new/{causeId}" })
	public String createCauses(Map<String, Object> model, @PathVariable("causeId") Integer idCause) {
		Donation donation= new Donation();
		Cause cause= this.causeService.findById(idCause).get();
		donation.setCause(cause);
		model.put("donation", donation);
		return "donations/createDonationForm";
	}
	
	@PostMapping(value = "/donations/new/{causeId}")
	public String processCreationForm(@Valid Donation donation,BindingResult result,@PathVariable("causeId") Integer idCause,Principal p,Map<String, Object> model) {

		Cause cause= this.causeService.findById(idCause).get();
		donation.setCause(cause);
		
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.put("donation", donation);
			return "donations/createDonationForm";
			}
		else {
			
			try {
				this.donationService.newDonation(p, donation);
				
			}catch(ClosedCauseException e) {
				model.put("message", "No puedes donar a una causa que ya ha alcanzado su objetivo");
			}
			
			return "welcome";
		}
	}
	

}