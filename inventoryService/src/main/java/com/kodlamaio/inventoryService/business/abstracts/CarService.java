package com.kodlamaio.inventoryService.business.abstracts;

import java.util.List;

import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.getAll.GetAllCarsResponse;
import com.kodlamaio.inventoryService.business.responses.getById.GetCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;

public interface CarService {
	List<GetAllCarsResponse> getAll();

	CreateCarResponse add(CreateCarRequest createCardRequest);

	UpdateCarResponse update(UpdateCarRequest updateCarRequest);

	GetCarResponse getById(String id);

	void delete(String id);

	void checkIfCarAvailable(String id);
	
	void changeState(String carId, int state);
	
}
