package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.car.CarCreatedEvent;
import com.kodlamaio.common.events.car.CarDeletedEvent;
import com.kodlamaio.common.events.car.CarUpdatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.CarService;
import com.kodlamaio.inventoryService.business.abstracts.ModelService;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;

import com.kodlamaio.inventoryService.business.responses.getAll.GetAllCarsResponse;
import com.kodlamaio.inventoryService.business.responses.getById.GetCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryService.dataAccess.CarRepository;
import com.kodlamaio.inventoryService.entities.Car;
import com.kodlamaio.inventoryService.kafka.InventoryProducer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CarManager implements CarService {
	private ModelMapperService modelMapperService;
	private CarRepository carRepository;
	private InventoryProducer inventoryProducer;
	private ModelService modelService;

	@Override
	public List<GetAllCarsResponse> getAll() {
		List<Car> cars = this.carRepository.findAll();
		List<GetAllCarsResponse> response = cars.stream()
				.map(car -> this.modelMapperService.forResponse().map(car, GetAllCarsResponse.class))
				.collect(Collectors.toList());

		return response;
	}

	@Override
	public CreateCarResponse add(CreateCarRequest createCarRequest) {
		checkIfCarExistsByPlate(createCarRequest.getPlate());
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		car.setId(UUID.randomUUID().toString());
		Car createdCar = this.carRepository.save(car);

		CarCreatedEvent carCreatedEvent = this.modelMapperService.forRequest().map(createdCar,
				CarCreatedEvent.class);
		carCreatedEvent.setMessage("Car Created");
		carCreatedEvent.setCarId(createdCar.getId());
		carCreatedEvent.setModelId(createdCar.getModel().getId());
		carCreatedEvent.setModelName(createdCar.getModel().getName());
		carCreatedEvent.setBrandId(createdCar.getModel().getBrand().getId());
		carCreatedEvent.setBrandName(createdCar.getModel().getBrand().getName());
		this.inventoryProducer.sendMessage(carCreatedEvent);
		
		CreateCarResponse createCarResponse = this.modelMapperService.forResponse().map(car, CreateCarResponse.class);
		return createCarResponse;
	}

	@Override
	public UpdateCarResponse update(UpdateCarRequest updateCarRequest) {
		checkIfCarExistsById(updateCarRequest.getId());
		checkIfCarExistsByPlate(updateCarRequest.getPlate());
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		Car updatedCar = this.carRepository.save(car);
		
		CarUpdatedEvent carUpdatedEvent = this.modelMapperService.forRequest().map(updatedCar, CarUpdatedEvent.class);
		carUpdatedEvent.setMessage("Car Updated");
		carUpdatedEvent.setCarId(updatedCar.getId());
		carUpdatedEvent.setModelId(updatedCar.getModel().getId());
		carUpdatedEvent.setModelName(updatedCar.getModel().getName());
		carUpdatedEvent.setBrandId(updatedCar.getModel().getBrand().getId());
		carUpdatedEvent.setBrandName(updatedCar.getModel().getBrand().getName());
		this.inventoryProducer.sendMessage(carUpdatedEvent);
		
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
		
		CarDeletedEvent carDeletedEvent = new CarDeletedEvent();
		carDeletedEvent.setCarId(id);
		carDeletedEvent.setMessage("Car Deleted");
		
		this.inventoryProducer.sendMessage(carDeletedEvent);

	}

	private void checkIfCarExistsById(String id) {
		Car car = this.carRepository.findById(id).get();
		if (car == null) {
			throw new BusinessException("CAR.NOT.EXISTS");
		}
	}

	private void checkIfCarExistsByPlate(String plate) {
		Car car = this.carRepository.findByPlate(plate);
		if (car != null) {
			throw new BusinessException("CAR.EXISTS");
		}
	}

	@Override
	public void checkIfCarAvailable(String id) {
		Car car = this.carRepository.findById(id).get();
		if (car.getState() != 1) {
			throw new BusinessException("CAR.NOT.AVAILABLE");
		}
	}

	@Override
	public void changeState(String carId, int state) {
		Car car = this.carRepository.findById(carId).get();
		car.setState(state);
		carRepository.save(car);

	}

}
