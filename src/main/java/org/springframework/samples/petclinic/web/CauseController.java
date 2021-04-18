package org.springframework.samples.petclinic.web;

import java.util.Map;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CauseController {

	private static String VIEWS_CAUSE_CREATE_FORM = "cause/CreateCauseForm";
	
	private final CauseService causeService;
	
	@Autowired
	public CauseController(final CauseService causeService) {
		this.causeService = causeService;
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
	public ModelAndView showOwner(@PathVariable("causeId") final int causeId) {
		final ModelAndView mav = new ModelAndView("causes/causeDetails");
		mav.addObject(this.causeService.findById(causeId).get());
		return mav;
	}

}