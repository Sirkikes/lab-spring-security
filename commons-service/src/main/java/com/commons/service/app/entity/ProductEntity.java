package com.commons.service.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name="products")
public class ProductEntity implements Serializable{

	private static final long serialVersionUID = -4091040283242475180L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    private String name;
    private Double price;
    
    @Column(name = "creat_at")
	@Temporal(TemporalType.DATE)
    private Date createAt;
    
    @Transient
    private Integer port;

	public ProductEntity() {

	}

	public ProductEntity(Long idProduct, String name, Double price, Date createAt, Integer port) {
		super();
		this.idProduct = idProduct;
		this.name = name;
		this.price = price;
		this.createAt = createAt;
		this.port = port;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	
	
	
}
