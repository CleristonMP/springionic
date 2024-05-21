package com.cmp.springionic.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cmp.springionic.domain.Category;
import com.cmp.springionic.dto.CategoryDTO;
import com.cmp.springionic.repositories.CategoryRepository;
import com.cmp.springionic.services.exceptions.DataIntegrityException;
import com.cmp.springionic.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();
		return list.stream().map(obj -> new CategoryDTO(obj)).collect(Collectors.toList());
	}

	public Category find(Long id) {
		Optional<Category> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()));
	}

	public Category insert(Category obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Category update(Category obj) {
		this.find(obj.getId());
		return repository.save(obj);
	}

	public void delete(Long id) {
		this.find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
		}
	}
}
