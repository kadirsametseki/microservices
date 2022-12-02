package com.kodlamaio.rentalService.business.abstracts;

import com.kodlamaio.rentalService.business.requests.CreateRentalRequest;
import com.kodlamaio.rentalService.business.responses.CreateRentalResponse;

public interface RentalService {
	CreateRentalResponse add(CreateRentalRequest createRentalRequest);
}
