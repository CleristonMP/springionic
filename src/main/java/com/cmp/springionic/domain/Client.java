package com.cmp.springionic.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cmp.springionic.domain.enums.ClientType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_client")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String cpfOrCnpj;
	private Integer type;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
	private final List<Address> address = new ArrayList<>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tb_phone")
	private final Set<String> phones = new HashSet<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "client")
	private final List<Order> orders = new ArrayList<>();

	public Client(Long id, String name, String email, String cpfOrCnpj, ClientType type) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpfOrCnpj = cpfOrCnpj;
		this.type = type.getCod();
	}
	
	public ClientType getType() {
		return ClientType.toEnum(type);
	}
	
	public void SetType(ClientType type) {
		this.type = type.getCod();
	}
}
