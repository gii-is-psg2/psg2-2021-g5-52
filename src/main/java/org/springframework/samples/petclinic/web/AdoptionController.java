package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdoptionController {

	private final PetService petService;

	@Autowired
	public AdoptionController(final ApplicationService applicationService, final PetService petService) {
		super();
		this.petService = petService;
	}

	@GetMapping(value = { "/adoptions" })
	public String showPetsForAdoptionList(final Map<String, Object> model, Principal p) {
		List<Pet> petsForAdoption= this.petService.findPetsForAdoption(p.getName());
		model.put("pets", petsForAdoption);
		
		return "adoptions/petsForAdoption";
	}

	@GetMapping(value = { "/adoptions/selectPets" })
	public String selectPets(final Map<String, Object> model, final Principal p) {
		final List<Pet> pets = this.petService.findPetsNotAdoptionByUsername(p.getName());
		model.put("pets", pets);
		
		return "/adoptions/selectPets";
	}

	@GetMapping(value = "/adoptions/pet/{petId}")
	public String createAdoption(final Map<String, Object> model, final Principal p, @PathVariable("petId")  int petId) {
		
		
		if(this.petService.isOwnerOf(petId, p.getName())) {
			this.petService.setInAdoption(petId);
			model.put("message", "Has dado en adopción una mascota correctamente, otros usuarios podrán verlo y  solicitar su adopción");

		} else {
			model.put("message", "No puedes dar en adopcion una mascota que no es tuya");
		}
		return this.showPetsForAdoptionList(model, p);
	}

	@GetMapping("/adoptions/{adoptionId}")
	public String showAdoption(@PathVariable("adoptionId") final int adoptionId, final Map<String, Object> model) {
		Application adoption = new Application();
//		adoption = this.applicationService.getAdoptionsById(adoptionId).get();
		model.put("adoption", adoption);
		return "adoptions/adoptionDetails";
	}

}
