package com.item.service.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
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
import com.item.service.app.model.Item;
import com.item.service.app.services.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@RefreshScope
@RestController
public class ItemController {
	
	private static Logger log = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;
	
	@Value("${configuracion.texto}")
	private String texto;

	@GetMapping("/listItem")
	public List<Item> list() {
		return itemService.findAll();
	}

	@HystrixCommand(fallbackMethod = "alternativeMethod")
	@GetMapping("/list/{id}/cant/{cant}")
	public Item detail(@PathVariable Long id, @PathVariable Integer cant) {
		return itemService.findById(id, cant);
	}
	
	public Item alternativeMethod(Long id, Integer cantidad) {
		Item item = new Item();
		ProductEntity product = new ProductEntity();
		
		item.setCant(cantidad);
		product.setIdProduct(id);
		product.setName("Camara Sony");
		product.setPrice(500.00);
		item.setProduct(product);
		return item;
	}
	
	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto){
		
		log.info(texto);
		
		Map<String, String> json = new HashMap<>();
		json.put("texto", texto);
		json.put("puerto", puerto);
		
		if(env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
			json.put("autor.email", env.getProperty("configuracion.autor.email"));
		}
		
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductEntity crear(@RequestBody ProductEntity product) {
		return itemService.save(product);
	}
	
	@PutMapping("/edit/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductEntity edit(@RequestBody ProductEntity product, @PathVariable Long id) {
		return itemService.update(product, id);
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		itemService.delete(id);
	}
	
	
	
	

}
