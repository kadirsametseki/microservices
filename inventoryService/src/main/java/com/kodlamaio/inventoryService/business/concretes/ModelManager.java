package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kodlamaio.inventoryService.business.abstracts.ModelService;
import com.kodlamaio.inventoryService.business.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllModelsResponse;
import com.kodlamaio.inventoryService.dataAccess.ModelRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ModelManager implements ModelService{

	private ModelRepository modelRepository;
	
	@Override
	public List<GetAllModelsResponse> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreateModelResponse add(CreateModelRequest createModelRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
