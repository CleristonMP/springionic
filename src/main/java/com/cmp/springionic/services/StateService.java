package com.cmp.springionic.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmp.springionic.dto.StateDTO;
import com.cmp.springionic.repositories.StateRepository;

@Service
public class StateService {

	@Autowired
	private StateRepository repository;

	public List<StateDTO> findAll() {
		return repository.findAllByOrderByName().stream()
				.map(stt -> new StateDTO(stt)).collect(Collectors.toList());
	}
}
