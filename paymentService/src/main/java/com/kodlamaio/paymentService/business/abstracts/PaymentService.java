package com.kodlamaio.paymentService.business.abstracts;

import java.util.List;

import com.kodlamaio.paymentService.business.requests.create.CreatePaymentRequest;
import com.kodlamaio.paymentService.business.requests.update.UpdatePaymentRequest;
import com.kodlamaio.paymentService.business.responses.create.CreatePaymentResponse;
import com.kodlamaio.paymentService.business.responses.create.UpdatePaymentResponse;
import com.kodlamaio.paymentService.business.responses.get.GetAllPaymentsResponse;

public interface PaymentService {
	CreatePaymentResponse add(CreatePaymentRequest createPaymentRequest);
	List<GetAllPaymentsResponse> getAll();
	UpdatePaymentResponse update(UpdatePaymentRequest request, String id);
}