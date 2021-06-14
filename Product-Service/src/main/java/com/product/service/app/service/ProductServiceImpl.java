package com.product.service.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commons.service.app.entity.ProductEntity;
import com.product.service.app.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductEntity> findAll() {
        return (List<ProductEntity>) productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductEntity findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

	@Override
	@Transactional
	public ProductEntity save(ProductEntity product) {
		return productRepository.save(product);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}
}
