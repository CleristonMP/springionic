package com.cmp.springionic.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
// 	Método antigo
//	public Page<CategoryDTO> findAllPaged(Integer page, Integer linesPerPage, String orderBy, String direction) {
//		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
//		Page<Category> pageObj = repository.findAll(pageRequest);
//		return pageObj.map(obj -> new CategoryDTO(obj));
//	}
	
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {
	Page<Category> page = repository.findAll(pageable);
	return page.map(obj -> new CategoryDTO(obj));
}

	public Category findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()));
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		dto.setId(null);
		Category entity = repository.save(this.fromDTO(dto));
		return new CategoryDTO(entity);
	}

	public CategoryDTO update(CategoryDTO dto) {
		Category entity = this.findById(dto.getId());
		this.updateData(entity, dto);
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	public void delete(Long id) {
		this.findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
		}
	}
	
	public Category fromDTO(CategoryDTO objDto) {
		return new Category(objDto.getId(), objDto.getName());
	}
	
	private void updateData(Category entity, CategoryDTO dto) {
		entity.setName(dto.getName());
	}
}
