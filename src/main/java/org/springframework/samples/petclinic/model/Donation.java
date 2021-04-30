package org.springframework.samples.petclinic.model;

import static org.junit.Assert.assertNotEquals;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.validation.constraints.Digits;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "donations")
public class Donation extends BaseEntity {

		@NotNull
		@Digits(fraction=2,integer=999999)
		@Positive
		@Column(name = "amount")
		private Double amount;
		

		@Column(name = "date")
		@Temporal(TemporalType.DATE)
	    @DateTimeFormat(pattern="yyyy-MM-dd")
		private Date date;

		
		@ManyToOne
		@JoinColumn(name = "causa_id")
		private Cause cause;
		
		
		@ManyToOne
		@JoinColumn(name = "owner_id")
		private Owner owner;

}

