package com.cmp.springionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmp.springionic.domain.Category;
import com.cmp.springionic.domain.Product;
import com.cmp.springionic.dto.ProductDTO;
import com.cmp.springionic.repositories.CategoryRepository;
import com.cmp.springionic.repositories.ProductRepository;
import com.cmp.springionic.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public Product findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Product.class.getName()));
	}
	
	public Page<ProductDTO> search(String name, List<Long> ids, Pageable pageable) {
		List<Category> categories = categoryRepository.findAllById(ids);
		Page<Product> page = repository.findDistinctByNameContainingAndCategoriesIn(name, categories, pageable);
		return page.map(prod -> new ProductDTO(prod));
	}
}
