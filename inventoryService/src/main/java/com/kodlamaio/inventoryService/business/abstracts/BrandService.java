package com.kodlamaio.inventoryService.business.abstracts;

import java.util.List;

import com.kodlamaio.inventoryService.business.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryService.business.responses.getAll.GetAllBrandsResponse;
import com.kodlamaio.inventoryService.business.responses.getById.GetBrandResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateBrandResponse;

public interface BrandService {
	List<GetAllBrandsResponse> getAll();
	CreateBrandResponse add(CreateBrandRequest createBrandRequest);
	UpdateBrandResponse update(UpdateBrandRequest updateBrandRequest);
	GetBrandResponse getById(String id);
	void delete(String id);
}
