package org.springframework.samples.petclinic.web;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CauseController {

	private static final String VIEWS_CAUSE_CREATE_FORM = "causes/createCauseForm";
	
	private final CauseService causeService;
	
	@Autowired
	public CauseController(final CauseService causeService) {
		this.causeService = causeService;
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
	public String processCreationForm(@Valid final Cause cause, final BindingResult result) {
		if (result.hasErrors()) {
			return CauseController.VIEWS_CAUSE_CREATE_FORM;
		}
		else {
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