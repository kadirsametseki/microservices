package com.kodlamaio.invoiceService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.invoiceService.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceService.business.requests.create.CreateInvoiceRequest;
import com.kodlamaio.invoiceService.business.responses.create.CreateInvoiceResponse;
import com.kodlamaio.invoiceService.business.responses.get.GetAllInvoicesResponse;
import com.kodlamaio.invoiceService.dataAccess.abstracts.InvoiceRepository;
import com.kodlamaio.invoiceService.entities.Invoice;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService{

	private InvoiceRepository invoiceRepository;
	private ModelMapperService modelMapperService;
	
	@Override
	public List<GetAllInvoicesResponse> getAll() {
		List<Invoice> invoices = this.invoiceRepository.findAll();
		List<GetAllInvoicesResponse> response = invoices.stream().map(invoice->this.modelMapperService.forResponse().map(invoices, GetAllInvoicesResponse.class)).collect(Collectors.toList());
		return response;	
	}
	
	
	@Override
	public CreateInvoiceResponse add(CreateInvoiceRequest createInvoiceRequest) {
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice.setId(UUID.randomUUID().toString());
		invoiceRepository.save(invoice);
		CreateInvoiceResponse createInvoiceResponse = this.modelMapperService.forResponse().map(invoice, CreateInvoiceResponse.class);
		return createInvoiceResponse;
	}

}
