package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String showAdoptionsList(final Map<String, Object> model) {
		model.put("adoptions", this.adoptionsService.findAll());

		return "adoptions/adoptionsList";
	}

	@GetMapping(value = { "/adoptions/new" })
	public String createAdoption(final Map<String, Object> model) {
		final Adoption adoption = new Adoption();
		final List<Pet> pets = this.adoptionsService.findAllPet().stream().collect(Collectors.toList());
		model.put("pets", pets);
		model.put("adoption", adoption);
		return AdoptionController.VIEWS_ADOPTION_CREATE_FORM;
	}

	@PostMapping(value = "/adoptions/new")
	public String processCreationForm(@Valid final Adoption adoption, final Principal p, final BindingResult result, @RequestParam(required=false) final Pet pet) {
		if (result.hasErrors()) {
			return AdoptionController.VIEWS_ADOPTION_CREATE_FORM;
		} else {
			final Owner owner = this.ownerService.findOwnerByUsername(p.getName());
			adoption.setOwner(owner);
			adoption.setPet(pet);
			final Collection<Application> solicitudes = new ArrayList<Application>();
			adoption.setApplications(solicitudes);
			this.adoptionsService.saveAdoptions(adoption);

			return "redirect:/adoptions/" + adoption.getId();
		}
	}

	@GetMapping("/adoptions/{adoptionId}")
	public String showAdoption(@PathVariable("adoptionId") final int adoptionId, final Map<String, Object> model) {
		Adoption adoption = new Adoption();
		adoption = this.adoptionsService.getAdoptionsById(adoptionId).get();
		model.put("adoption", adoption);
		return "adoptions/adoptionDetails";
	}

}
