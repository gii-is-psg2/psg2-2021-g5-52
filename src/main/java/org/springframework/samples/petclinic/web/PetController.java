/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

	private static final String		VIEWS_PETS_CREATE_OR_UPDATE_FORM	= "pets/createOrUpdatePetForm";

	private final PetService		petService;
	private final OwnerService		ownerService;
	private final OwnerController	ownerController;


	@Autowired
	public PetController(final PetService petService, final OwnerService ownerService, final OwnerController ownerController) {
		this.petService = petService;
		this.ownerController = ownerController;
		this.ownerService = ownerService;

	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.petService.findPetTypes();
	}

	@InitBinder("owner")
	public void initOwnerBinder(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("pet")
	public void initPetBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new PetValidator());
	}

	@GetMapping(value = "/pets/new")
	public String initCreationForm(@PathVariable("ownerId") final int ownerId, final ModelMap model) {
		final Pet pet = new Pet();
		final Owner owner = this.ownerService.findOwnerById(ownerId);
		owner.addPet(pet);
		model.put("pet", pet);
		return PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/pets/new")
	public String processCreationForm(@Valid final Pet pet, @PathVariable("ownerId") final int ownerId, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("pet", pet);
			return PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				final Owner owner = this.ownerService.findOwnerById(ownerId);
				owner.addPet(pet);
				this.petService.savePet(pet);
			} catch (final DuplicatedPetNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM;
			}
			return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping(value = "/pets/{petId}/edit")
	public String initUpdateForm(@PathVariable("petId") final int petId, final ModelMap model) {
		final Pet pet = this.petService.findPetById(petId);
		model.put("pet", pet);
		return PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/pets/{petId}/edit")
	public String processUpdateForm(@Valid final Pet pet, final BindingResult result, final Owner owner, @PathVariable("petId") final int petId, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("pet", pet);
			return PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			final Pet petToUpdate = this.petService.findPetById(petId);
			BeanUtils.copyProperties(pet, petToUpdate, "id", "owner", "visits");
			try {
				this.petService.savePet(petToUpdate);
			} catch (final DuplicatedPetNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM;
			}
			return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping(value = "pets/{petId}/delete")
	public String deletePet(@PathVariable("petId") final int petId, final ModelMap model) {
		final Pet pet = this.petService.findPetById(petId);
		try {
			this.petService.deletePetAndVisists(pet);
			model.addAttribute("message", "Â¡Mascota correctamente eliminada!");

		} catch (final DataAccessException e) {

			model.addAttribute("message", "Las mascota no pudo ser eliminada");
		}
		return this.ownerController.initFindForm(model);
	}

}