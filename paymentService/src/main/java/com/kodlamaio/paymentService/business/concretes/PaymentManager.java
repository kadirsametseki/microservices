package com.kodlamaio.paymentService.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.paymentService.business.abstracts.PaymentService;

import com.kodlamaio.paymentService.business.requests.create.CreatePaymentRequest;
import com.kodlamaio.paymentService.business.requests.update.UpdatePaymentRequest;
import com.kodlamaio.paymentService.business.responses.create.CreatePaymentResponse;
import com.kodlamaio.paymentService.business.responses.create.UpdatePaymentResponse;
import com.kodlamaio.paymentService.business.responses.get.GetAllPaymentsResponse;
import com.kodlamaio.paymentService.dataAccess.abstracts.PaymentRepository;
import com.kodlamaio.paymentService.entities.Payment;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {

	private PaymentRepository paymentRepository;
	private ModelMapperService modelMapperService;

	@Override
	public CreatePaymentResponse add(CreatePaymentRequest createPaymentRequest) {
		
		Payment payment = modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		payment.setId(UUID.randomUUID().toString());
		paymentRepository.save(payment);

		Payment createdPayment = paymentRepository.save(payment);
		CreatePaymentResponse response = modelMapperService.forResponse().map(payment, CreatePaymentResponse.class);
		return response;

	}

	@Override
	public List<GetAllPaymentsResponse> getAll() {
		List<Payment> payments = paymentRepository.findAll();
		List<GetAllPaymentsResponse> response = payments.stream()
				.map(payment -> modelMapperService.forResponse().map(payment, GetAllPaymentsResponse.class)).toList();

		return response;
	}

	@Override
	public UpdatePaymentResponse update(UpdatePaymentRequest request, String id) {
		checkIfExistsById(id);
		Payment payment = modelMapperService.forRequest().map(request, Payment.class);
		payment.setId(id);
		paymentRepository.save(payment);
		UpdatePaymentResponse response = modelMapperService.forResponse().map(payment, UpdatePaymentResponse.class);

		return response;
	}

	void checkIfExistsById(String id) {
		Payment payment = this.paymentRepository.findById(id).orElse(null);
		if(payment==null) {
			throw new BusinessException("CARD.NOT.EXISTS");
		}
		
	}
	
	

}
