package com.kodlamaio.paymentService.business.responses.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentResponse {
	private String id;

	private double totalPrice;

	private String rentalId;

	private int status;

	private String cardNo;

	private String cardHolder;

	private double cardBalance;
}
