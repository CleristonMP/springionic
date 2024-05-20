package com.cmp.springionic.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmp.springionic.domain.Client;
import com.cmp.springionic.services.ClientService;

@RestController
@RequestMapping(value="/clients")
public class ClientResource {
	
	@Autowired
	private ClientService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Client> find(@PathVariable Long id) {
		Client obj = service.search(id);
		return ResponseEntity.ok().body(obj);
	}
}
