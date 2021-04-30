
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.service.exceptions.BookingSavingException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Booking extends BaseEntity {

	@Column
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	startDate;

	@Column
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	endDate;

	@OneToOne()
	@JoinColumn(name = "pet_id")
	private Pet			pet;


	public void checkConcurrency(final LocalDate newStartDate, final LocalDate newEndDate) throws BookingSavingException {
		final LocalDate oldStartDate = this.getStartDate();
		final LocalDate oldEndDate = this.getEndDate();

		if (oldStartDate.compareTo(newStartDate) <= 0 && oldEndDate.compareTo(newStartDate) >= 0) {
			throw new BookingSavingException("La fecha de inicio de la nueva reserva está dentro de otra reserva");
		}

		else if (oldStartDate.compareTo(newEndDate) <= 0 && oldEndDate.compareTo(newEndDate) >= 0) {
			throw new BookingSavingException("La fecha de fin de la nueva reserva está dentro de otra reserva");
		}

		else if (newStartDate.compareTo(oldStartDate) <= 0 && newEndDate.compareTo(oldStartDate) >= 0) {
			throw new BookingSavingException("La fecha de inicio de otra reserva está dentro de la nueva reserva");
		}

		else if (newStartDate.compareTo(oldEndDate) <= 0 && newEndDate.compareTo(oldEndDate) >= 0) {
			throw new BookingSavingException("La fecha de fin de otra reserva está dentro de la nueva reserva");
		}
	}

}
