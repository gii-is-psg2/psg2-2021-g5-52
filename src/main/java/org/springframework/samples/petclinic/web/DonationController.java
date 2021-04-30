
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

	private final DonationService	donationService;
	private final CauseService		causeService;

	private static final String		MESSAGE	= "message";


	@Autowired
	public DonationController(final DonationService donationService, final CauseService causeService) {
		this.donationService = donationService;
		this.causeService = causeService;
	}

	@GetMapping(value = {
		"/donations/new/{causeId}"
	})
	public String createCauses(final Map<String, Object> model, @PathVariable("causeId") final Integer idCause) {
		final Donation donation = new Donation();
		final Optional<Cause> optCause = this.causeService.findById(idCause);
		if (optCause.isPresent()) {
			donation.setCause(optCause.get());
			model.put("donation", donation);
			return "donations/createDonationForm";
		} else {
			model.put(DonationController.MESSAGE, "No se ha encontrado la Causa");
			return "/donations/new/" + idCause;
		}
	}

	@PostMapping(value = "/donations/new/{causeId}")
	public String processCreationForm(@Valid final Donation donation, final BindingResult result, @PathVariable("causeId") final Integer idCause, final Principal p, final Map<String, Object> model) {
		final Optional<Cause> optCause = this.causeService.findById(idCause);
		if (optCause.isPresent()) {
			donation.setCause(optCause.get());
			if (result.hasErrors()) {
				model.put("donation", donation);
				return "donations/createDonationForm";
			} else {

				try {
					this.donationService.newDonation(p, donation);

				} catch (final ClosedCauseException e) {
					model.put(DonationController.MESSAGE, "No puedes donar a una causa que ya ha alcanzado su objetivo");
				}

				return "welcome";
			}
		} else {
			model.put(DonationController.MESSAGE, "No se ha encontrado la Causa");
			return "/donations/new/" + idCause;
		}
	}

}
