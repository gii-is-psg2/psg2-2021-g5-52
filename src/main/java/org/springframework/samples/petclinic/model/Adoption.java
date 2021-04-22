package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "adoptions")
public class Adoption extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@OneToOne
	@JoinColumn(name = "owner_id", referencedColumnName = "id")
	private Owner owner;
	
	@NotEmpty
	@Column(name = "description")
	private String description;
	
	@OneToMany(mappedBy = "adoption")
	@Column(name = "applications")
	private Collection<Application> applications;
	
	


}