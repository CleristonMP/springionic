package com.cmp.springionic.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmp.springionic.dto.CityDTO;
import com.cmp.springionic.repositories.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;

	public List<CityDTO> findAll(Long stateId) {
		return repository.findCities(stateId).stream()
				.map(c -> new CityDTO(c)).collect(Collectors.toList());
	}
}
