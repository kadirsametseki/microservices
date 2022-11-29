package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kodlamaio.inventoryService.business.abstracts.CarService;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryService.dataAccess.CarRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CarManager implements CarService{

	private CarRepository carRepository;
	
	@Override
	public List<GetAllCarsResponse> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreateCarResponse add(CreateCarRequest createCardRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
