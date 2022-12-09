package com.kodlamaio.common.events.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDeletedEvent {
	private String message;	
	private String carId;
}