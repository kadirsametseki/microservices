package com.kodlamaio.rentalService.business.concretes;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.RentalCreatedEvent;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;

import com.kodlamaio.rentalService.business.abstracts.RentalService;
import com.kodlamaio.rentalService.business.requests.CreateRentalRequest;
import com.kodlamaio.rentalService.business.responses.CreateRentalResponse;
import com.kodlamaio.rentalService.dataAccess.RentalRepository;
import com.kodlamaio.rentalService.entities.Rental;
import com.kodlamaio.rentalService.kafka.RentalProducer;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@Service
public class RentalManager implements RentalService{

	private RentalRepository rentalRepository;
	private ModelMapperService modelMapperService;
	private RentalProducer rentalProducer;
	
	
	@Override
	public CreateRentalResponse add(CreateRentalRequest createRentalRequest) {
		
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		rental.setId(UUID.randomUUID().toString());
		this.rentalRepository.save(rental);
		
		
		RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
		rentalCreatedEvent.setCarId(rental.getCarId());
		rentalCreatedEvent.setMessage("Rental Created");
		this.rentalProducer.sendMessage(rentalCreatedEvent);
		
		CreateRentalResponse createRentalResponse = this.modelMapperService.forResponse().map(rental, CreateRentalResponse.class);
		return createRentalResponse;
	}
	
	
	
	
}



