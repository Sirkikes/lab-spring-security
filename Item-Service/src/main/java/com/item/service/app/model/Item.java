package com.item.service.app.model;

import com.commons.service.app.entity.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Item {
	
	private ProductEntity product;
	private Integer cant;
	
	public Double getTotal() {
		return product.getPrice() * cant.doubleValue();
	}

	public Item() {
	}

	public Item(ProductEntity product, Integer cant) {
		this.product = product;
		this.cant = cant;
	}
}
