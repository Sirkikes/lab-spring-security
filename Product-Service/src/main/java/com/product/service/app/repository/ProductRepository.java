package com.product.service.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.commons.service.app.entity.ProductEntity;

public interface ProductRepository extends CrudRepository<ProductEntity, Long>{

}
