package com.kodlamaio.invoiceService.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoices")
public class Invoice {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "carId")
	private String carId;
	
	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "brandName")
	private String brandName;
	
	@Column(name = "modelName")
	private String modelName;
	
	@Column(name = "modelYear")
	private int modelYear;

	@Column(name = "totalPrice")
	private double totalPrice;

	@Column(name = "rentedForDays")
	private int rentedForDays;
}