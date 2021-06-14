package com.item.service.app.services;

import java.util.List;

import com.item.service.app.model.Item;
import com.commons.service.app.entity.ProductEntity;

public interface ItemService {
	
	public List<Item> findAll();
	public Item findById(Long id, Integer cant);
	public ProductEntity save(ProductEntity product);
	public ProductEntity update (ProductEntity product, Long id);
	public void delete(Long id);

}
