package com.kodlamaio.invoiceService.business.responses.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllInvoicesResponse {
	private String id;
	private String carId;
	private String firstName;
	private String lastName;
	private String modelName;
	private String brandName;
	private int modelYear;
	private double dailyPrice;
	private double totalPrice;
	private int rentedForDays;
}	
