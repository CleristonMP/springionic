package com.cmp.springionic.dto;

import java.io.Serializable;

import com.cmp.springionic.domain.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Double price;
	
	public ProductDTO(Product obj) {
		this.id = obj.getId();
		this.name = obj.getName();
		this.price = obj.getPrice();
	}
}
