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
package org.springframework.samples.petclinic.model;




import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "causes")
public class Cause extends BaseEntity {


	@NotBlank
	@Column(name = "name")
	private String name;
	

	@OneToOne
	@JoinColumn(name = "owner_id", referencedColumnName = "id")
	private Owner owner;
	
	@NotBlank
	@Column(name = "description")
	private String description;
	
	@NotNull
	@Positive
	@Column(name = "budget_target", precision=10, scale=2)
	private Double budgetTarget;
	
	@NotBlank
	@Column(name = "organization")
	private String organization;

	@OneToMany(mappedBy = "cause")
	@Column(name = "donaciones")
	private Collection<Donation> donaciones;
	
	@Transient
	public Double getSumaDonaciones() {
		Double res=0.0;
		for(final Donation donacion:this.donaciones) {
			res += donacion.getAmount();
		}
		return res;
	}
	
	
}
