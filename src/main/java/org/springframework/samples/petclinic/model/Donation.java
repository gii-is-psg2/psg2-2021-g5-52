package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "donations")
public class Donation extends BaseEntity {

		@NotNull
		@Column(name = "amount", precision = 10 , scale = 2)
		@Positive
		private Double amount;
		
		@NotNull
		@Column(name = "date")
		@Temporal(TemporalType.DATE)
	    @DateTimeFormat(pattern="yyyy-MM-dd")
		private Date date;

		@NotBlank
		@ManyToOne
		@JoinColumn(name = "causa_id")
		private Cause cause;
		
		@NotBlank
		@ManyToOne
		@JoinColumn(name = "owner_id")
		private Owner owner;

}

