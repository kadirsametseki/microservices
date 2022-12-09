package com.kodlamaio.inventoryService.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.rental.RentalCreatedEvent;
import com.kodlamaio.common.events.rental.RentalUpdatedCarEvent;
import com.kodlamaio.inventoryService.business.abstracts.CarService;

@Service
public class RentalConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RentalConsumer.class);
	
	private final CarService carService;

	public RentalConsumer(CarService carService) {
		this.carService = carService;
	}

	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "create")
	public void consume(RentalCreatedEvent event) {
		LOGGER.info(String.format("Car is rented => %s", event.toString()));
		carService.changeState(event.getCarId(), 3);
		LOGGER.info("Car state changed!");
		// save the order event into the database
		
	}
	
	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "update")
	public void consume(RentalUpdatedCarEvent event) {
		LOGGER.info(String.format("The car has been updated => %s", event.toString()));
		carService.changeState(event.getOldCarId(), 1);
		carService.changeState(event.getNewCarId(), 3);
		// save the order event into the database
	}
	
	

}