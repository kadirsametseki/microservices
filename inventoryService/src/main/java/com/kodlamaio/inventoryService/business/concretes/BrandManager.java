package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.BrandService;
import com.kodlamaio.inventoryService.business.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryService.business.responses.getAll.GetAllBrandsResponse;
import com.kodlamaio.inventoryService.business.responses.getById.GetBrandResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateBrandResponse;
import com.kodlamaio.inventoryService.dataAccess.BrandRepository;
import com.kodlamaio.inventoryService.entities.Brand;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BrandManager implements BrandService {
	private BrandRepository brandRepository;
	private ModelMapperService modelMapperService;

	@Override
	public List<GetAllBrandsResponse> getAll() {
		List<Brand> brands = this.brandRepository.findAll();
		List<GetAllBrandsResponse> response = brands.stream()
				.map(brand -> this.modelMapperService.forResponse().map(brand, GetAllBrandsResponse.class))
				.collect(Collectors.toList());

		return response;
	}

	
	@Override
	public CreateBrandResponse add(CreateBrandRequest createBrandRequest) {
		checkIfBrandExistsByName(createBrandRequest.getName());
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		brand.setId(UUID.randomUUID().toString());
		this.brandRepository.save(brand);
		
		CreateBrandResponse createBrandResponse = this.modelMapperService.forResponse().map(brand, CreateBrandResponse.class);
		return createBrandResponse;
	}


	@Override
	public UpdateBrandResponse update(UpdateBrandRequest updateBrandRequest) {
		checkIfBrandExistsById(updateBrandRequest.getId());
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		this.brandRepository.save(brand);
		
		UpdateBrandResponse updateBrandResponse = this.modelMapperService.forResponse().map(brand, UpdateBrandResponse.class);
		return updateBrandResponse;
	}
	
	@Override
	public GetBrandResponse getById(String id) {
		checkIfBrandExistsById(id);
		Brand brand = this.brandRepository.findById(id).get();
		GetBrandResponse brandResponse = this.modelMapperService.forResponse().map(brand, GetBrandResponse.class);
		return brandResponse;
	}
	
	@Override
	public void delete(String id) {
		checkIfBrandExistsById(id);
		this.brandRepository.deleteById(id);
		
	}
	
	
	private void checkIfBrandExistsById(String id) {
		Brand brand = this.brandRepository.findById(id).get();
		if(brand == null) {
			throw new BusinessException("BRAND.NOT.EXISTS");
		}
	}
	
	
	private void checkIfBrandExistsByName(String name) {
		Brand currentBrand = this.brandRepository.findByName(name);
		if (currentBrand != null) {
			throw new BusinessException("BRAND.EXISTS");
		}
	}
	
}
