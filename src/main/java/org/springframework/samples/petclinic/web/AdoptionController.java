package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdoptionController {
	
	private final AdoptionService adoptionsService;
	private final PetService petService;

	@Autowired
	public AdoptionController(final AdoptionService adoptionsService, final PetService petService) {
		super();
		this.adoptionsService = adoptionsService;
		this.petService = petService;
	}
	
	@GetMapping("/pet/{petId}/new")
    public String addNewAdopcion(@PathVariable("petId") final Integer petId,final ModelMap model) {
       
		final Pet pet = this.petService.findPetById(petId);
		final Adoption adop = new Adoption();
		adop.setPet(pet);
		model.addAttribute("adopcion",adop);
        
        return "adopcion/createEditAdoptionForm";
    }
	
	
	@PostMapping("/pet/{petId}/new")
    public String saveNewAdopcion(@Valid final Adoption adoption, final BindingResult result, final ModelMap model) {
        if (result.hasErrors()) {
        	model.addAttribute("adopcion", adoption);
        	return "adopcion/createEditAdoptionForm";
        } else {
        	this.adoptionsService.saveAdoptions(adoption);
        	final Integer num = adoption.getPet().getOwner().getId();
        	return "redirect:/owners/"+Integer.toString(num);
        }
    }
	
	
}
