package com.kodlamaio.rentalService.business.requests;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateRentalRequest {

	private String carId;
	private LocalDateTime dateStarted;
	private int rentedForDays;
	private double dailyPrice;
	private double totalPrice;
}
