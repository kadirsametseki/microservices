package com.kodlamaio.rentalService.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentalService.business.abstracts.RentalService;
import com.kodlamaio.rentalService.business.requests.CreateRentalRequest;
import com.kodlamaio.rentalService.business.responses.CreateRentalResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/rentals")
@AllArgsConstructor
public class RentalsController {
	private RentalService rentalService;
	
	@PostMapping
	public CreateRentalResponse add(@RequestBody CreateRentalRequest createRentalRequest) {
		System.out.println(createRentalRequest.getCarId());
		return this.rentalService.add(createRentalRequest);
	}
}
