package com.kodlamaio.filterService.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.car.CarCreatedEvent;
import com.kodlamaio.filterService.business.abstracts.FilterService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InventoryConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryConsumer.class);
	private FilterService filterService;

	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "createcar")
	public void consume(CarCreatedEvent event) {
		LOGGER.info(String.format("Car event received in filter service => %s", event.toString()));
		filterService.addCar(event);
		
	}
}