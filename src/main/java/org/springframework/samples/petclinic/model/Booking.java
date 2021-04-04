package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking extends BaseEntity{
    
    @Column
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate; 

    @Column
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate endDate;

    @OneToOne()
    @JoinColumn(name = "pet_id")
    private Pet pet;



}