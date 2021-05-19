/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;


import java.util.Map;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.samples.petclinic.model.Specialty;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;


import org.springframework.web.servlet.ModelAndView;


/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {

	private static final String VIEWS_VETS_CREATE_OR_UPDATE_FORM = "vets/CreateOrUpdateVetForm";
	
	private final VetService vetService;
	
	@Autowired
	public VetController(VetService clinicService) {
		this.vetService = clinicService;
	}

	@GetMapping(value = { "/vets" })
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	

	
	@GetMapping(value = { "/vets/new" })
	public String createVet(Map<String, Object> model) {
		Vet vet = new Vet();
		List<Specialty> specialties = this.vetService.findAllSpeciality().stream().collect(Collectors.toList());
		
		model.put("specialties", specialties);
		model.put("vet", vet);
		return VIEWS_VETS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/vets/new")
	public String processCreationForm(@Valid Vet vet, BindingResult result, @RequestParam(required=false) Set<Specialty> specialties) {
		if (result.hasErrors()) {
			return VIEWS_VETS_CREATE_OR_UPDATE_FORM;
		}
		else {
			vet.setSpecialty(specialties);
			vetService.saveVet(vet);
			
			return "redirect:/vets/" + vet.getId();
		}
	}
	
	@GetMapping("/vets/{vetId}")
	public ModelAndView showOwner(@PathVariable("vetId") final int vetId) {
		ModelAndView mav = new ModelAndView("vets/vetDetails");
		mav.addObject(this.vetService.findVetById(vetId));
		return mav;
	}
	
	@GetMapping(value = "/vets/{vetId}/edit")
	public String initUpdateForm(@PathVariable("vetId") int vetId, ModelMap model) {
		Vet vet = this.vetService.findVetById(vetId);
		List<Specialty> specialties = this.vetService.findAllSpeciality().stream().collect(Collectors.toList());
		
		model.put("specialties", specialties);
		model.put("vet", vet);
		return VIEWS_VETS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/vets/{vetId}/edit")
	public String processUpdateForm(@Valid Vet vet, BindingResult result,@PathVariable("vetId") int vetId, ModelMap model, @RequestParam(required=false) Set<Specialty> specialties) {
		if (result.hasErrors()) {
			model.put("vet", vet);
			return VIEWS_VETS_CREATE_OR_UPDATE_FORM;
		}
		else {
			vet.setId(vetId);
			vet.setSpecialty(specialties);
			this.vetService.saveVet(vet);
			return "redirect:/vets/{vetId}";
		}
	}

	 
	@GetMapping(value = "/vets/{vetId}/delete")
    public String deletePet(@PathVariable("vetId") int vetId, ModelMap model) {
 		Vet vet = this.vetService.findVetById(vetId);
 		try {
 			vetService.delete(vet);
 			model.addAttribute("message", "¡Veterinario eliminado correctamente!");

 		}catch(DataAccessException e) {
 			
				model.addAttribute("message", "Este veterinario no pudo ser eliminado");
 		}
 		return showVetList(model);
	}
	

}
