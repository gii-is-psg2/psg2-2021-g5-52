package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ApplicationController {
	
	private final ApplicationService applicationService;
	private final PetService petService;
	private final AdoptionController adoptionController;
	
	
	@Autowired
	public ApplicationController(final ApplicationService applicationService,final AdoptionController adoptionController, final PetService petService) {
		this.applicationService = applicationService;
		this.adoptionController=adoptionController;
		this.petService=petService;
	}
	
	@GetMapping(value = { "/applications/createApplicationForm/{petId}" })
	public String createApplication(final Map<String, Object> model, @PathVariable("petId") final Integer idpet) {
		final Application application = new Application();
		application.setPet(petService.findPetById(idpet));
		model.put("application", application);
		return "applications/createApplicationForm";
	}
	
	@GetMapping(value = { "/applications/applicationsList" })
    public String listOfApplications(final Map<String, Object> model) {
		final List<Application> applications = this.applicationService.findNotClosed();
		model.put("applicationList", applications);
		return "/applications/applicationsList";
    }
	
	@PostMapping(value = "/applications/createApplicationForm/{petId}")
	public String processCreationForm(@Valid final Application application,final BindingResult result,@PathVariable("petId") final Integer idpet,final Principal p,final Map<String, Object> model) {
		Pet pet = petService.findPetById(idpet);
		application.setPet(petService.findPetById(idpet));
		
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.put("application", application);
			return "applications/createApplicationForm";
			}
		else {
			if(!petService.isOwnerOf(idpet, p.getName()) && pet.isInAdoption()) {
				applicationService.newApplication(p, application);
			}else {
				model.put("message", "No puedes adoptar una mascota propia o que no este en adopcion");
			}
			
			return this.adoptionController.showPetsForAdoptionList(model, p);
		}
	}

}
