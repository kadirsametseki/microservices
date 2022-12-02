package com.kodlamaio.inventoryService.business.responses.getById;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBrandResponse {
	private String id;
	private String name;
}
