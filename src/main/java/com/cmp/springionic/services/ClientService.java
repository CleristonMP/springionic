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

import com.cmp.springionic.domain.Address;
import com.cmp.springionic.domain.City;
import com.cmp.springionic.domain.Client;
import com.cmp.springionic.domain.enums.ClientType;
import com.cmp.springionic.dto.ClientDTO;
import com.cmp.springionic.dto.ClientNewDTO;
import com.cmp.springionic.repositories.AddressRepository;
import com.cmp.springionic.repositories.CityRepository;
import com.cmp.springionic.repositories.ClientRepository;
import com.cmp.springionic.services.exceptions.DataIntegrityException;
import com.cmp.springionic.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private CityRepository cityRepository;

	public Client findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}

// 	Método antigo
//	public Page<ClientDTO> findAllPaged(Integer page, Integer linesPerPage, String orderBy, String direction) {
//		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
//		Page<Client> pageObj = repository.findAll(pageRequest);
//		return pageObj.map(obj -> new ClientDTO(obj));
//	}

	public List<ClientDTO> findAll() {
		List<Client> list = repository.findAll();
		return list.stream().map(obj -> new ClientDTO(obj)).collect(Collectors.toList());
	}

	public Page<ClientDTO> findAllPaged(Pageable pageable) {
		Page<Client> page = repository.findAll(pageable);
		return page.map(obj -> new ClientDTO(obj));
	}

	@Transactional
	public ClientNewDTO insert(ClientNewDTO dto) {
		dto.setId(null);
		Client entity = repository.save(this.fromDTO(dto));
		addressRepository.saveAll(entity.getAddress());
		return new ClientNewDTO(entity);
	}

	public ClientDTO update(ClientDTO dto) {
		Client entity = this.findById(dto.getId());
		this.updateData(entity, dto);
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}

	public void delete(Long id) {
		this.findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedido(s).");
		}
	}

	public Client fromDTO(ClientDTO objDto) {
		return new Client(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
	}

	public Client fromDTO(ClientNewDTO objDto) {
		Client cli = new Client(null, objDto.getName(), objDto.getEmail(), objDto.getCpfOrCnpj(),
				ClientType.toEnum(objDto.getType()));
		City city = cityRepository.getReferenceById(objDto.getCityId());
		Address address = new Address(null, objDto.getPublicPlace(), objDto.getNumber(), objDto.getComplement(),
				objDto.getDistrict(), objDto.getZipCode(), cli, city);
		cli.getAddress().add(address);
		cli.getPhones().add(objDto.getPhone1());
		if (objDto.getPhone2() != null) {
			cli.getPhones().add(objDto.getPhone2());
		}
		if (objDto.getPhone3() != null) {
			cli.getPhones().add(objDto.getPhone3());
		}
		return cli;
	}

	private void updateData(Client entity, ClientDTO dto) {
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
	}
}
