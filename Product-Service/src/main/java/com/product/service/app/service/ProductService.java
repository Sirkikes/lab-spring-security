package com.product.service.app.service;

import java.util.List;

import com.commons.service.app.entity.ProductEntity;

public interface ProductService {
	
	public List<ProductEntity> findAll();
    public ProductEntity findById(Long id);
    
    public ProductEntity save(ProductEntity product);
    
    public void deleteById(Long id);
    
    

}
