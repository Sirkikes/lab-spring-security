package com.item.service.app.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.commons.service.app.entity.ProductEntity;
import com.item.service.app.model.Item;


@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

	@Autowired
	private RestTemplate clientRest;

	@Override
	public List<Item> findAll() {
		List<ProductEntity> products = Arrays.asList(clientRest.getForObject("http://product-service/list", ProductEntity[].class));
		return products.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cant) {

		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());

		ProductEntity product = clientRest.getForObject("http://product-service/list/{id}", ProductEntity.class, pathVariables);
		return new Item(product, cant);
	}

	@Override
	public ProductEntity save(ProductEntity product) {
		
		HttpEntity<ProductEntity> body = new HttpEntity<ProductEntity>(product);
		ResponseEntity<ProductEntity> response= clientRest.exchange("http://product-service/create", HttpMethod.POST, body, ProductEntity.class);
		ProductEntity productResponse = response.getBody();
		return productResponse;
	}

	@Override
	public ProductEntity update(ProductEntity product, Long id) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		
		HttpEntity<ProductEntity> body= new HttpEntity<ProductEntity>(product);
		ResponseEntity<ProductEntity> response = clientRest.exchange("http://product-service/edit/{id}", 
				HttpMethod.PUT, body, ProductEntity.class, pathVariables);
		
		return response.getBody();
	}

	@Override
	public void delete(Long id) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		clientRest.delete("http://product-service/delete/{id}", pathVariables);
		
	}

}
