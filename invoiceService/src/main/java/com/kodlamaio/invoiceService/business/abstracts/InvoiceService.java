package com.kodlamaio.invoiceService.business.abstracts;

import java.util.List;

import com.kodlamaio.invoiceService.business.requests.create.CreateInvoiceRequest;
import com.kodlamaio.invoiceService.business.responses.create.CreateInvoiceResponse;
import com.kodlamaio.invoiceService.business.responses.get.GetAllInvoicesResponse;

public interface InvoiceService {
    List<GetAllInvoicesResponse> getAll();
    CreateInvoiceResponse add(CreateInvoiceRequest request);
}