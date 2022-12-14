package com.kodlamaio.inventoryService.business.abstracts;

import java.util.List;

import com.kodlamaio.inventoryService.business.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryService.business.responses.getAll.GetAllModelsResponse;
import com.kodlamaio.inventoryService.business.responses.getById.GetModelResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateModelResponse;

public interface ModelService{
	List<GetAllModelsResponse> getAll();
	CreateModelResponse add(CreateModelRequest createModelRequest);
	UpdateModelResponse update(UpdateModelRequest updateModelRequest);
	GetModelResponse getById(String id);
	void delete(String id);
}
