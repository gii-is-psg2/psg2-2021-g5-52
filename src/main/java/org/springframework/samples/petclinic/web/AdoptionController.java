package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdoptionController {

	private static String VIEWS_ADOPTION_CREATE_FORM = "adoptions/createAdoptionsForm";

	private final AdoptionService adoptionsService;
	private final PetService petService;
	private final OwnerService ownerService;

	@Autowired
	public AdoptionController(final AdoptionService adoptionsService, final PetService petService, final OwnerService ownerService) {
		super();
		this.adoptionsService = adoptionsService;
		this.petService = petService;
		this.ownerService = ownerService;
	}

	@GetMapping(value = { "/adoptions" })
	public String showPetsForAdoptionList(final Map<String, Object> model) {
		List<Pet> petsForAdoption= this.petService.findPetsForAdoption();
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
		} else {
			model.put("message", "No puedes dar en adopcion una mascota que no es tuya");
		}
		return this.showPetsForAdoptionList(model);
	}

	@GetMapping("/adoptions/{adoptionId}")
	public String showAdoption(@PathVariable("adoptionId") final int adoptionId, final Map<String, Object> model) {
		Adoption adoption = new Adoption();
		adoption = this.adoptionsService.getAdoptionsById(adoptionId).get();
		model.put("adoption", adoption);
		return "adoptions/adoptionDetails";
	}

}
