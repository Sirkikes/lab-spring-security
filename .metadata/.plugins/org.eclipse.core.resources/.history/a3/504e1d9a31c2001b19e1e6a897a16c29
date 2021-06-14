package com.item.service.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.item.service.app.clients.ProductClientRest;
import com.item.service.app.model.Item;
import com.commons.service.app.entity.ProductEntity;

@Service("serviceFeign")
public class ItemServiceFeign implements ItemService{
	
	@Autowired
	private ProductClientRest clientFeign;

	@Override
	public List<Item> findAll() {
		return clientFeign.list().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cant) {
		return new Item(clientFeign.listById(id), cant);
	}

	@Override
	public ProductEntity save(ProductEntity product) {
		return clientFeign.create(product);
	}

	@Override
	public ProductEntity update(ProductEntity product, Long id) {
		return clientFeign.update(product, id);
	}

	@Override
	public void delete(Long id) {
		clientFeign.delete(id);
	}
	

}
