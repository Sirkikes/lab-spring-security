package com.product.service.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.commons.service.app.entity.ProductEntity;
import com.product.service.app.service.ProductService;

@RestController
public class ProductController {

	// @Autowired
	// private Environment env;

	@Value("${server.port}")
	private Integer port;

	@Autowired
	private ProductService productService;

	@GetMapping("/list")
	public List<ProductEntity> list() {
		return productService.findAll().stream().map(product -> {
			// producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			product.setPort(port);
			return product;
		}).collect(Collectors.toList());

	}

	@GetMapping("/list/{id}")
	public ResponseEntity<ProductEntity> listById(@PathVariable Long id) {
		ProductEntity product = productService.findById(id);
		//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		product.setPort(port);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<ProductEntity> create(@RequestBody ProductEntity product) {
		return  new ResponseEntity(productService.save(product), HttpStatus.CREATED);
		
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<ProductEntity> edit(@RequestBody ProductEntity product, @PathVariable Long id) {
		ProductEntity productDb = productService.findById(id);
		
		productDb.setName(product.getName());
		productDb.setPrice(product.getPrice());
        
        return new ResponseEntity(productService.save(productDb), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		productService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
