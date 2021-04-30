package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CauseController {

	private static final String VIEWS_CAUSE_CREATE_FORM = "causes/createCauseForm";
	
	private final CauseService causeService;
	private final OwnerService ownerService;
	
	@Autowired
	public CauseController(final CauseService causeService, final OwnerService ownerService) {
		this.causeService = causeService;
		this.ownerService = ownerService;
	}
	

	@GetMapping(value = { "/causes" })
	public String showCausesList(final Map<String, Object> model) {
		model.put("causes", this.causeService.findAll());
		
		return "causes/causesList";
	}
	
	@GetMapping(value = { "/causes/new" })
	public String createCauses(final Map<String, Object> model) {
		final Cause cause = new Cause();
		model.put("cause", cause);
		return CauseController.VIEWS_CAUSE_CREATE_FORM;
	}
	
	@PostMapping(value = "/causes/new")
	public String processCreationForm(@Valid final Cause cause, final Principal p, final BindingResult result) {
		if (result.hasErrors()) {
			return CauseController.VIEWS_CAUSE_CREATE_FORM;
		}
		else {
			final Owner owner = this.ownerService.findOwnerByUsername(p.getName());
			cause.setOwner(owner);
			final Collection<Donation> donaciones = new ArrayList<>();
			cause.setDonaciones(donaciones);
			this.causeService.save(cause);
			
			return "redirect:/causes/" + cause.getId();
		}
	}
	
	@GetMapping("/causes/{causeId}")
	public String showCause(@PathVariable("causeId") final int causeId , final Map<String, Object> model) {
		final Optional<Cause> optCause = this.causeService.findById(causeId);
		if(optCause.isPresent()) {
			model.put("cause", optCause.get());
			return "causes/causeDetails";
		} else {
			model.put("message", "No se ha encontrado la Causa");
			return "causes/";
		}
		
	}

}