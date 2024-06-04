package com.cmp.springionic.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cmp.springionic.domain.Client;
import com.cmp.springionic.dto.ClientDTO;
import com.cmp.springionic.dto.ClientNewDTO;
import com.cmp.springionic.services.ClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/clients")
public class ClientResource {
	
	@Autowired
	private ClientService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Client> findById(@PathVariable Long id) {
		Client obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<ClientDTO>> findAll() {
		List<ClientDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
//	Método antigo
//	@GetMapping(value = "/page")
//	public ResponseEntity<Page<ClientDTO>> findPage(
//			@RequestParam(value = "page", defaultValue = "0") Integer page, 
//			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
//			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy, 
//			@RequestParam(value = "direction", defaultValue = "ASC") String direction
//			) {
//		Page<ClientDTO> pageObj = service.findAllPaged(page, linesPerPage, orderBy, direction);
//		return ResponseEntity.ok().body(pageObj);
//	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClientDTO>> findAllPaged(Pageable pageable) {
		Page<ClientDTO> page = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(page);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClientNewDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClientDTO dto, @PathVariable Long id) {
		dto.setId(id);
		dto = service.update(dto);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
