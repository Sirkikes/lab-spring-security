package com.item.service.app.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.commons.service.app.entity.ProductEntity;


@FeignClient(name = "product-service")
public interface ProductClientRest {
	
	@GetMapping("/list")
	public List<ProductEntity> list();
	
	@GetMapping("/list/{id}")
    public ProductEntity listById(@PathVariable Long id);
	
	@PostMapping("/create")
	public ProductEntity create(@RequestBody ProductEntity product);
	
	@PutMapping("/edit/{id}")
	public ProductEntity update(@RequestBody ProductEntity producto, @PathVariable Long id);
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id);

}
