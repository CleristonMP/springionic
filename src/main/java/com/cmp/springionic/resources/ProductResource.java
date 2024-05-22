package com.cmp.springionic.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmp.springionic.domain.Product;
import com.cmp.springionic.dto.ProductDTO;
import com.cmp.springionic.resources.utils.URL;
import com.cmp.springionic.services.ProductService;

@RestController
@RequestMapping(value="/products")
public class ProductResource {
	
	@Autowired
	private ProductService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Product obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAllPaged(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "categories", defaultValue = "") String categories,
			Pageable pageable) {
		String nameDecoded = URL.decodeParam(name);
		List<Long> ids = URL.decodeLongList(categories);
		Page<ProductDTO> page = service.search(nameDecoded, ids, pageable);
		return ResponseEntity.ok().body(page);
	}
}
