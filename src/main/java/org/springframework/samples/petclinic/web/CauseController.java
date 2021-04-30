package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


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

	private static String VIEWS_CAUSE_CREATE_FORM = "causes/createCauseForm";
	
	private CauseService causeService;
	private OwnerService ownerService;
	
	@Autowired
	public CauseController(CauseService causeService, OwnerService ownerService) {
		this.causeService = causeService;
		this.ownerService = ownerService;
	}
	

	@GetMapping(value = { "/causes" })
	public String showCausesList(Map<String, Object> model) {
		model.put("causes", this.causeService.findAll());
		
		return "causes/causesList";
	}
	
	@GetMapping(value = { "/causes/new" })
	public String createCauses(Map<String, Object> model) {
		Cause cause = new Cause();
		model.put("cause", cause);
		return CauseController.VIEWS_CAUSE_CREATE_FORM;
	}
	
	@PostMapping(value = "/causes/new")
	public String processCreationForm(@Valid Cause cause, Principal p, BindingResult result) {
		if (result.hasErrors()) {
			return CauseController.VIEWS_CAUSE_CREATE_FORM;
		}
		else {
			Owner owner = ownerService.findOwnerByUsername(p.getName());
			cause.setOwner(owner);
			Collection<Donation> donaciones = new ArrayList<Donation>();
			cause.setDonaciones(donaciones);
			this.causeService.save(cause);
			
			return "redirect:/causes/" + cause.getId();
		}
	}
	
	@GetMapping("/causes/{causeId}")
	public String showCause(@PathVariable("causeId") int causeId , Map<String, Object> model) {
		Cause cause = new Cause();
		cause = causeService.findById(causeId).get();
		model.put("cause", cause);
		return "causes/causeDetails";
	}

}