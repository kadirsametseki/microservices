package com.kodlamaio.paymentService.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name="rentalId")
	private String rentalId;
	
	@Column(name = "cardNo")
	private String cardNo;
	
	@Column(name = "carBalance")
	private double cardBalance;
	
	@Column(name = "cardHolder")
	private String cardHolder;
	
	@Column(name="totalPrice")
	private double totalPrice;
	
	@Column(name = "cardDate")
	private LocalDate cardDate;
	
	@Column(name = "cvv")
	private int cvv;
	
	@Column(name="status")
	private int status;
	
	
}
