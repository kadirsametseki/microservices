package com.kodlamaio.rentalService.business.requests.create;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {
	
	@NotBlank
	@NotNull
	private String carId;
	
	@NotNull
	@Min(1)
	private  int rentedForDays;
	
	@Min(150)
	private double dailyPrice;
	
	@NotBlank
	@NotNull
	@Size(min = 16, max =16)
	private String cardNo;
	
	@NotBlank
	@NotNull
	@Size(min = 4)
	private String cardHolder;
	
	@Min(0)
	private double cardBalance;
	
}
