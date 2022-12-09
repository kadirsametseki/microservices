package com.kodlamaio.rentalService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feign.Headers;

@FeignClient(value = "paymentClien", url = "http://localhost:9011/")
public interface PaymentClient {
	@RequestMapping(method =RequestMethod.GET,value ="payment-service/api/payments/checkBalanceEnough/{balance}/{totalPrice}")
	@Headers(value = "Content-Type: application/json")
	void checkBalanceEnough(@PathVariable double balance, @PathVariable double totalPrice);
	
}