package com.kodlamaio.inventoryService.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.inventoryService.business.abstracts.BrandService;
import com.kodlamaio.inventoryService.business.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryService.business.responses.getAll.GetAllBrandsResponse;
import com.kodlamaio.inventoryService.business.responses.getById.GetBrandResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateBrandResponse;

import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/api/brands")
@AllArgsConstructor
public class BrandsController {
	
	private BrandService brandService;
	
	@GetMapping
	public List<GetAllBrandsResponse> getAll(){
		return this.brandService.getAll();
	}
	
	@PostMapping
	public CreateBrandResponse add(@RequestBody CreateBrandRequest createBrandRequest) {
		return this.brandService.add(createBrandRequest);
	}
	
	@PutMapping()
	public UpdateBrandResponse update(@RequestBody UpdateBrandRequest updateBrandRequest) {
		return this.brandService.update(updateBrandRequest);
	}
	
	@GetMapping("/{id}")
	public GetBrandResponse getById(@PathVariable String id) {
		return this.brandService.getById(id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		this.brandService.delete(id);
	}
	
	
}
