
package org.springframework.samples.petclinic.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.ApplicationRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

	private final ApplicationRepository	applicationRepository;
	private final OwnerService			ownerService;
	private final PetService			petService;


	@Autowired
	public ApplicationService(final ApplicationRepository applicationRepository, final OwnerService ownerService, final PetService petService) {
		this.applicationRepository = applicationRepository;
		this.ownerService = ownerService;
		this.petService = petService;
	}

	@Transactional
	public void newApplication(final Principal p, final Application application) {

		final Owner owner = this.ownerService.findOwnerByUsername(p.getName());
		application.setOwner(owner);

		this.save(application);

	}
	@Transactional
	public void save(final Application application) {

		this.applicationRepository.save(application);

	}

	@Transactional
	public Iterable<Application> findAll() throws DataAccessException {
		return this.applicationRepository.findAll();
	}

	@Transactional
	public List<Application> findApplicationsToMeNotClosed(final String username) throws DataAccessException {

		return this.applicationRepository.findAplicationsToMeNotClosed(username);

	}

	@Transactional
	public boolean isApplicationToMe(final String username, final Application app) {
		boolean res = false;
		if (app.getPet().getOwner().getUser().getUsername().equals(username)) {
			res = true;
		}
		return res;
	}

	@Transactional
	public Optional<Application> findById(final int id) {
		return this.applicationRepository.findById(id);
	}

	@Transactional
	public void rejectApplication(final Application app) {
		app.setClosed(true);
		this.applicationRepository.save(app);
	}

	@Transactional
	public void aceptApplication(final Application app) throws DuplicatedPetNameException {
		final Pet pet = app.getPet();
		pet.setOwner(app.getOwner());
		this.petService.savePet(pet);
		app.setClosed(true);
		this.applicationRepository.save(app);
	}
}
