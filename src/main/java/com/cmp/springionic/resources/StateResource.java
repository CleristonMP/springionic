package com.cmp.springionic.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmp.springionic.dto.CityDTO;
import com.cmp.springionic.dto.StateDTO;
import com.cmp.springionic.services.CityService;
import com.cmp.springionic.services.StateService;

@RestController
@RequestMapping(value = "/states")
public class StateResource {

	@Autowired
	private StateService service;

	@Autowired
	private CityService cityService;

	@GetMapping
	public ResponseEntity<List<StateDTO>> findAll() {
		List<StateDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{stateId}/cities")
	public ResponseEntity<List<CityDTO>> findCities(@PathVariable Long stateId) {
		List<CityDTO> list = cityService.findAll(stateId);
		return ResponseEntity.ok().body(list);
	}
}
