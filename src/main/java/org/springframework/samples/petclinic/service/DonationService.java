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
package org.springframework.samples.petclinic.service;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.samples.petclinic.service.exceptions.ClosedCauseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonationService {

	
	private  DonationRepository donationRepository;
	private OwnerService ownerService;
	private CauseService causeService;

	@Autowired
	public DonationService(DonationRepository donationRepository, OwnerService ownerService,CauseService causeService) {
		this.donationRepository = donationRepository;
		this.ownerService=ownerService;
		this.causeService=causeService;
		
	}

	@Transactional
	public void newDonation(Principal p,Donation donation) throws ClosedCauseException {
		
	
		if(donation.getCause().getBudgetTarget() <= donation.getCause().getSumaDonaciones() ) {
			
			throw new ClosedCauseException();
		}
		
		Owner owner= this.ownerService.findOwnerByUsername(p.getName());
		donation.setOwner(owner);
		
		Date fechaActual= new Date();
		donation.setDate(fechaActual);
		
		this.save(donation);
		
	}
	@Transactional
	public void save(Donation donation) {
		
		donationRepository.save(donation);
		
	}

}
