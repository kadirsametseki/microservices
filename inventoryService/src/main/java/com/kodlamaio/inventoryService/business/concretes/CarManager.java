package com.kodlamaio.inventoryService.business.concretes;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.CarService;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;

import com.kodlamaio.inventoryService.business.responses.getAll.GetAllCarsResponse;
import com.kodlamaio.inventoryService.business.responses.getById.GetCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryService.dataAccess.CarRepository;
import com.kodlamaio.inventoryService.entities.Car;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CarManager implements CarService {
	private ModelMapperService modelMapperService;
	private CarRepository carRepository;

	@Override
	public List<GetAllCarsResponse> getAll() {
		List<Car> cars = this.carRepository.findAll();
		List<GetAllCarsResponse> response = cars.stream().map(car -> this.modelMapperService.forResponse().map(car, GetAllCarsResponse.class))
				.collect(Collectors.toList());

		return response;
	}
	
	@Override
	public CreateCarResponse add(CreateCarRequest createCarRequest) {
		checkIfCarExistsByPlate(createCarRequest.getPlate());
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		car.setId(UUID.randomUUID().toString());
		this.carRepository.save(car);
		
		CreateCarResponse createCarResponse = this.modelMapperService.forResponse().map(car, CreateCarResponse.class);
		return createCarResponse;
	}

	@Override
	public UpdateCarResponse update(UpdateCarRequest updateCarRequest) {
		checkIfCarExistsById(updateCarRequest.getId());
		checkIfCarExistsByPlate(updateCarRequest.getPlate());
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carRepository.save(car);
		
		UpdateCarResponse updateCarResponse = this.modelMapperService.forResponse().map(car, UpdateCarResponse.class);
		return updateCarResponse;
	}

	
	@Override
	public GetCarResponse getById(String id) {
		checkIfCarExistsById(id);
		Car car = this.carRepository.findById(id).get();
		GetCarResponse carResponse = this.modelMapperService.forResponse().map(car, GetCarResponse.class);
		return carResponse;
	}
	
	@Override
	public void delete(String id) {
		checkIfCarExistsById(id);
		this.carRepository.deleteById(id);
		
	}
	
	
	private void checkIfCarExistsById(String id) {
		
		if(!this.carRepository.findById(id).isPresent()) {
			throw new BusinessException("CAR.NOT.EXISTS");
		}
	}
	
	
	private void checkIfCarExistsByPlate(String plate) {
		
		if(this.carRepository.findByPlate(plate).isPresent()) {
			throw new BusinessException("CAR.EXISTS");
		}
	}

}
