package org.springframework.samples.petclinic.service;

import java.security.Principal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

	private final ApplicationRepository applicationRepository;
	private final OwnerService ownerService;
	private final AdoptionService adoptionService;

	@Autowired
	public ApplicationService(final ApplicationRepository applicationRepository, final OwnerService ownerService, final AdoptionService adoptionService) {
		this.applicationRepository = applicationRepository;
		this.ownerService=ownerService;
		this.adoptionService=adoptionService;
		
	}

	@Transactional
	public void newApplication(final Principal p,final Application application) {
	
		final Owner owner= this.ownerService.findOwnerByUsername(p.getName());
		application.setOwner(owner);
		
		this.save(application);
		
	}
	@Transactional
	public void save(final Application application) {
		
		this.applicationRepository.save(application);
		
	}
}
