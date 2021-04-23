package org.springframework.samples.petclinic.service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

	private final ApplicationRepository applicationRepository;
	private final OwnerService ownerService;

	@Autowired
	public ApplicationService(final ApplicationRepository applicationRepository, final OwnerService ownerService) {
		this.applicationRepository = applicationRepository;
		this.ownerService=ownerService;
		
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
	
	@Transactional	
	public Iterable<Application> findAll() throws DataAccessException {
		return applicationRepository.findAll();
	}	
	
	@Transactional
	public List<Application> findNotClosed() throws DataAccessException{
		return applicationRepository.findNotClosed();
	}
	
	
}
