package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApplicationController {
	
	private final ApplicationService applicationService;
	private final PetService petService;
	private final AdoptionController adoptionController;
	private final UserService userService;
	
	String message = "message";
	
	@Autowired
	public ApplicationController(final ApplicationService applicationService,final AdoptionController adoptionController, final PetService petService,UserService userService) {
		this.applicationService = applicationService;
		this.adoptionController=adoptionController;
		this.petService=petService;
		this.userService=userService;
	}
	
	@GetMapping(value = { "/applications/createApplicationForm/{petId}" })
	public String createApplication(final Map<String, Object> model, @PathVariable("petId") final Integer idpet) {
		final Application application = new Application();
		application.setPet(this.petService.findPetById(idpet));
		model.put("application", application);
		return "applications/createApplicationForm";
	}
	
	@GetMapping(value = { "/applications/applicationsList" })
    public String listOfApplications(final Map<String, Object> model, Principal p) {
		final List<Application> applications = this.applicationService.findApplicationsToMeNotClosed(p.getName());
		model.put("applicationList", applications);
		return "/applications/applicationsList";
    }
	
	@PostMapping(value = "/applications/createApplicationForm/{petId}")
	public String processCreationForm(@Valid final Application application,final BindingResult result,@PathVariable("petId") final Integer idpet,final Principal p,final Map<String, Object> model) {
		Pet pet = this.petService.findPetById(idpet);
		application.setPet(this.petService.findPetById(idpet));
		
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.put("application", application);
			return "applications/createApplicationForm";
			}
		else {
			if(!this.userService.userHaveRol(p.getName(), "owner")){
				model.put(this.message, "No puedes adoptar una mascota si no tienes un rol de propietario");

			}else if(!this.petService.isOwnerOf(idpet, p.getName()) && pet.isInAdoption()) {
				this.applicationService.newApplication(p, application);
			}else{
				model.put(this.message, "No puedes adoptar una mascota propia o que no este en adopcion");
			}
			
			return this.adoptionController.showPetsForAdoptionList(model, p);
		}
	}
	
	@GetMapping(value = { "/applications/reject/{applicationId}" })
	public String rejectApplication(final Map<String, Object> model, @PathVariable("applicationId") final Integer applicationId,Principal p) {
		
		Optional<Application> application= this.applicationService.findById(applicationId);
		if(application.isPresent()) {
			
			if(this.applicationService.isApplicationToMe(p.getName(),application.get())) {
				this.applicationService.rejectApplication(application.get());
				model.put(this.message,"Solicitud rechazada correctamente");

			}else {
				model.put(this.message,"No puedes rechazar una petición que no va dirigida a ti");
			}
			
		}else {
			model.put(this.message, "No puedes rechazar una petición que no existe");
			
		}	
		return this.listOfApplications(model, p);
	}
	@GetMapping(value = { "/applications/acept/{applicationId}" })
	public String aceptApplication(final Map<String, Object> model, @PathVariable("applicationId") final Integer applicationId,Principal p) {
		
		Optional<Application> application= this.applicationService.findById(applicationId);
		
		if(application.isPresent()) {
			
			if(this.applicationService.isApplicationToMe(p.getName(),application.get())) {
				this.applicationService.aceptApplication(application.get());
				
				model.put(this.message,"Solicitud aceptada correctamente");

			}else {
				model.put(this.message,"No puedes aceptar una petición que no va dirigida a ti");
			}
			
		}else {
			model.put(this.message, "No puedes aceptar una petición que no existe");
			
		}
		return this.listOfApplications(model, p);
	}

}
