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

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class OwnerController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

	private final OwnerService ownerService;
	private final UserService userService;
	private final AuthoritiesService authoritiesService;

	@Autowired
	public OwnerController(OwnerService ownerService, UserService userService, AuthoritiesService authoritiesService) {
		this.ownerService = ownerService;	
		this.userService = userService;
		this.authoritiesService = authoritiesService;
		}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/owners/new")
	public String initCreationForm(Map<String, Object> model) {
		User user = this.userService.getUserSession();
		if(this.userService.userHaveRol(user.getUsername(), "veterinarian") || this.userService.userHaveRol(user.getUsername(), "admin")){
			Owner owner = new Owner();
			model.put("owner", owner);
			return OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}else {
			return "redirect:/";
		}
	}

	@PostMapping(value = "/owners/new")
	public String processCreationForm(@Valid Owner owner, BindingResult result) {
		if (result.hasErrors()) {
			return OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			//creating owner, user and authorities
			this.ownerService.saveOwner(owner);
			
			return "redirect:/owners/" + owner.getId();
		}
	}
	
	@GetMapping(value="owners/delete/{ownerId}")
	public String deleteOwner(@PathVariable("ownerId") int ownerId,ModelMap modelMap) {
		User user = this.userService.getUserSession();
		if(this.userService.userHaveRol(user.getUsername(), "veterinarian") || this.userService.userHaveRol(user.getUsername(), "admin") || 
			this.ownerService.findOwnerByUsername(user.getUsername()).getId().equals(ownerId)){
			Owner owner= this.ownerService.findOwnerById(ownerId);
			try {
				this.ownerService.delete(owner);
				modelMap.addAttribute("message", "¡Propietario correctamente eliminado!");
					
			}catch(DataAccessException exception) {
					
					modelMap.addAttribute("message", "El propietario no pudo ser eliminado");
			}
				
			return this.initFindForm(modelMap);
		}else {
			return "redirect:/";
		}
	}

	
	
	

	@GetMapping(value = "/owners/find")
	public String initFindForm(Map<String, Object> model) {
		User user = this.userService.getUserSession();
		if(this.userService.userHaveRol(user.getUsername(), "veterinarian") || this.userService.userHaveRol(user.getUsername(), "admin")){
			model.put("owner", new Owner());
			return "owners/findOwners";
		}else {
			return "redirect:/";
		}
	}

	@GetMapping(value = "/owners")
	public String processFindForm(Owner owner, BindingResult result, Map<String, Object> model) {
		User user = this.userService.getUserSession();
		if(this.userService.userHaveRol(user.getUsername(), "veterinarian") || this.userService.userHaveRol(user.getUsername(), "admin")){
			// allow parameterless GET request for /owners to return all records
			if (owner.getLastName() == null) {
				owner.setLastName(""); // empty string signifies broadest possible search
			}
	
			// find owners by last name
			Collection<Owner> results = this.ownerService.findOwnerByLastName(owner.getLastName());
			if (results.isEmpty()) {
				// no owners found
				result.rejectValue("lastName", "notFound", "not found");
				return "owners/findOwners";
			}
			else if (results.size() == 1) {
				// 1 owner found
				owner = results.iterator().next();
				return "redirect:/owners/" + owner.getId();
			}
			else {
				// multiple owners found
				model.put("selections", results);
				return "owners/ownersList";
			}
		}else {
			return "redirect:/";
		}
	}

	@GetMapping(value = "/owners/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
		User user = this.userService.getUserSession();
		if(this.userService.userHaveRol(user.getUsername(), "veterinarian") || this.userService.userHaveRol(user.getUsername(), "admin") || 
			this.ownerService.findOwnerByUsername(user.getUsername()).getId().equals(ownerId)){
			Owner owner = this.ownerService.findOwnerById(ownerId);
			model.addAttribute(owner);
			return OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}else {
			return "redirect:/";
		}
	}

	@PostMapping(value = "/owners/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result,
			@PathVariable("ownerId") int ownerId) {
			owner.setId(ownerId);
			this.ownerService.saveOwner(owner);
			return "redirect:/owners/{ownerId}";
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/owners/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
		User user = this.userService.getUserSession();
		if(this.userService.userHaveRol(user.getUsername(), "veterinarian") || this.userService.userHaveRol(user.getUsername(), "admin") || 
			this.ownerService.findOwnerByUsername(user.getUsername()).getId().equals(ownerId)){
			ModelAndView mav = new ModelAndView("owners/ownerDetails");
			mav.addObject(this.ownerService.findOwnerById(ownerId));
			return mav;
		}else {
			ModelAndView mav = new ModelAndView("redirect:/");
			return mav;
		}
			
		
	}
	
	@GetMapping("/owners/myAccount")
	public String showOwner() {
		Owner owner = this.ownerService.findOwnerByUsername(this.userService.getUserSession().getUsername());
		return "redirect:/owners/"+owner.getId();
	}

}
