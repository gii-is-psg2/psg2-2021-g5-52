package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApplicationController {
	
	private final ApplicationService applicationService;
	private final AdoptionService adoptionService;
	private final AdoptionController adoptionController;
	
	
	@Autowired
	public ApplicationController(final ApplicationService applicationService, final AdoptionService adoptionService,final AdoptionController adoptionController) {
		this.applicationService = applicationService;
		this.adoptionService=adoptionService;
		this.adoptionController=adoptionController;
	}

	
	@GetMapping(value = { "/applications/new/{adoptionId}" })
	public String createApplications(final Map<String, Object> model, @PathVariable("adoptionId") final Integer idAdoption) {
		final Application application= new Application();
		final Adoption adoption= this.adoptionService.findById(idAdoption).get();
		application.setAdoption(adoption);
		model.put("application", application);
		return "applications/createApplicationForm";
	}
	
	@PostMapping(value = "/applications/new/{adoptionId}")
	public String processCreationForm(@Valid final Application application,final BindingResult result,@PathVariable("adoptionId") final Integer idAdoption,final Principal p,final Map<String, Object> model) {

		final Adoption adoption= this.adoptionService.findById(idAdoption).get();
		application.setAdoption(adoption);
		
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.put("application", application);
			return "applications/createApplicationForm";
			}
		else {
				this.applicationService.newApplication(p, application);
			
			return this.adoptionController.showPetsForAdoptionList(model);
		}
	}

}
