package com.kodlamaio.inventoryService.business.responses.getById;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarResponse {
	private String id;
	private String modelId;
	private double dailyPrice;
	private int modelYear;
	private String plate;
	private int state;
	private String brandName;
	private String colorName;

}
