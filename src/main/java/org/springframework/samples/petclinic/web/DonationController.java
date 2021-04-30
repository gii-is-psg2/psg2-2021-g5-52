package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.samples.petclinic.service.exceptions.ClosedCauseException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class DonationController {

	private final DonationService donationService;
	private final CauseService causeService;
	
	
	@Autowired
	public DonationController(DonationService donationService,CauseService causeService) {
		this.donationService = donationService;
		this.causeService=causeService;
	}

	
	@GetMapping(value = { "/donations/new/{causeId}" })
	public String createCauses(Map<String, Object> model, @PathVariable("causeId") Integer idCause) {
		Donation donation= new Donation();
		Optional<Cause> optCause = this.causeService.findById(idCause);
		if(optCause.isPresent()) {
			donation.setCause(optCause.get());
			model.put("donation", donation);
			return "donations/createDonationForm";
		}else {
			model.put("message", "No se ha encontrado la Causa");
			return "/donations/new/" + idCause;
		}
	}
	
	@PostMapping(value = "/donations/new/{causeId}")
	public String processCreationForm(@Valid Donation donation,BindingResult result,@PathVariable("causeId") Integer idCause,Principal p,Map<String, Object> model) {
		Optional<Cause> optCause = this.causeService.findById(idCause);
		if(optCause.isPresent()) {
			donation.setCause(optCause.get());
			
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
		}else {
			model.put("message", "No se ha encontrado la Causa");
			return "/donations/new/" + idCause;
		}
	}
	

}