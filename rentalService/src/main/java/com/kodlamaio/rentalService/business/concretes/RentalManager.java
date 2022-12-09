package com.kodlamaio.rentalService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.rental.RentalCreatedEvent;
import com.kodlamaio.common.events.rental.RentalInvoiceCreatedEvent;
import com.kodlamaio.common.events.rental.RentalPaymentCreatedEvent;
import com.kodlamaio.common.events.rental.RentalUpdatedCarEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentalService.business.abstracts.RentalService;
import com.kodlamaio.rentalService.business.requests.create.CreateRentalRequest;
import com.kodlamaio.rentalService.business.requests.update.UpdateRentalRequest;
import com.kodlamaio.rentalService.business.responses.create.CreateRentalResponse;
import com.kodlamaio.rentalService.business.responses.get.GetAllRentalsResponse;
import com.kodlamaio.rentalService.business.responses.update.UpdateRentalResponse;
import com.kodlamaio.rentalService.client.CarClient;
import com.kodlamaio.rentalService.client.PaymentClient;
import com.kodlamaio.rentalService.dataAccess.RentalRepository;
import com.kodlamaio.rentalService.entities.Rental;
import com.kodlamaio.rentalService.kafka.RentalCreateProducer;
import com.kodlamaio.rentalService.kafka.RentalInvoiceCreateProducer;
import com.kodlamaio.rentalService.kafka.RentalPaymentCreateProducer;
import com.kodlamaio.rentalService.kafka.RentalUpdateProducer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RentalManager implements RentalService {

	private RentalRepository rentalRepository;
	private ModelMapperService modelMapperService;
	private RentalCreateProducer rentalCreateProducer;
	private RentalUpdateProducer rentalUpdateProducer;
	private CarClient carClient;
	private PaymentClient paymentClient;
	private RentalPaymentCreateProducer rentalPaymentCreateProducer;
	private RentalInvoiceCreateProducer rentalInvoiceCreateProducer;

	@Override
	public CreateRentalResponse add(CreateRentalRequest createRentalRequest) {

		checkIfRentalExistsByCarId(createRentalRequest.getCarId());
		carClient.checkIfCarAvailable(createRentalRequest.getCarId());

		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		rental.setId(UUID.randomUUID().toString());
		double rentalTotalPrice = (createRentalRequest.getDailyPrice() * createRentalRequest.getRentedForDays());
		rental.setTotalPrice(rentalTotalPrice);
//		rental.setTotalPrice(createRentalRequest.getDailyPrice() * createRentalRequest.getRentedForDays());
		RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
		rentalCreatedEvent.setCarId(rental.getCarId());
		rentalCreatedEvent.setMessage("Rental Created");

		RentalPaymentCreatedEvent paymentCreatedEvent = new RentalPaymentCreatedEvent();
		paymentCreatedEvent.setRentalId(rental.getId());
		paymentCreatedEvent.setCardNo(createRentalRequest.getCardNo());
		paymentCreatedEvent.setCardHolder(createRentalRequest.getCardHolder());
		paymentCreatedEvent.setCardBalance(createRentalRequest.getCardBalance());
		paymentCreatedEvent.setTotalPrice(rentalTotalPrice);

		RentalInvoiceCreatedEvent invoiceCreatedEvent = new RentalInvoiceCreatedEvent();
		invoiceCreatedEvent.setRentalId(rental.getId());
		invoiceCreatedEvent.setCardHolder(createRentalRequest.getCardHolder());
		invoiceCreatedEvent.setTotalPrice(rentalTotalPrice);

		rentalPaymentCreateProducer.sendMessage(paymentCreatedEvent);

		paymentClient.checkBalanceEnough(paymentCreatedEvent.getCardBalance(), paymentCreatedEvent.getTotalPrice());

		rentalRepository.save(rental);

		rentalCreateProducer.sendMessage(rentalCreatedEvent);
		rentalInvoiceCreateProducer.sendMessage(invoiceCreatedEvent);

		CreateRentalResponse createRentalResponse = this.modelMapperService.forResponse().map(rental,
				CreateRentalResponse.class);
		return createRentalResponse;
	}

	@Override
	public UpdateRentalResponse update(UpdateRentalRequest updateRentalRequest) {
		checkIfRentalExistsByCarId(updateRentalRequest.getId());
		carClient.checkIfCarAvailable(updateRentalRequest.getCarId());

		RentalUpdatedCarEvent rentalUpdatedCarEvent = new RentalUpdatedCarEvent();

		Rental rental = this.rentalRepository.findById(updateRentalRequest.getId());
		rentalUpdatedCarEvent.setOldCarId(rental.getCarId());

		rental.setCarId(updateRentalRequest.getCarId());
		rental.setDailyPrice(updateRentalRequest.getDailyPrice());
		rental.setRentedForDays(updateRentalRequest.getRentedForDays());

		double totalPrice = updateRentalRequest.getDailyPrice() * updateRentalRequest.getRentedForDays();
		rental.setTotalPrice(totalPrice);

		Rental rentalUpdated = this.rentalRepository.save(rental);

		rentalUpdatedCarEvent.setNewCarId(rentalUpdated.getCarId());
		rentalUpdatedCarEvent.setMessage("Rental Updated");

		rentalUpdateProducer.sendMessage(rentalUpdatedCarEvent); // kafkaya gidecek mesaj

		UpdateRentalResponse updateRentalResponse = this.modelMapperService.forResponse().map(rental,
				UpdateRentalResponse.class);

		return updateRentalResponse;

	}

	@Override
	public List<GetAllRentalsResponse> getAll() {
		List<Rental> rentals = this.rentalRepository.findAll();

		List<GetAllRentalsResponse> response = rentals.stream()
				.map(rental -> this.modelMapperService.forResponse().map(rental, GetAllRentalsResponse.class))
				.collect(Collectors.toList());

		return response;
	}

	private void checkIfRentalExistsByCarId(String id) {
		Rental rental = this.rentalRepository.findById(id);
		if (rental != null) {
			throw new BusinessException("RENTAL.CARID.EXISTS");
		}
	}

}
