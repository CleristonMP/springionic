package com.cmp.springionic.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.cmp.springionic.domain.enums.ClientType;
import com.cmp.springionic.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
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
import lombok.Setter;

@Entity
@Table(name = "tb_client")
@Getter
@Setter
@EqualsAndHashCode
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@Column(unique = true)
	private String email;
	private String cpfOrCnpj;
	private Integer type;
	
	@JsonIgnore
	private String password;
	
	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private final List<Address> address = new ArrayList<>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tb_phone")
	private final Set<String> phones = new HashSet<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "tb_roles")
	private final Set<Integer> roles = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "client")
	private final List<Order> orders = new ArrayList<>();
	
	private String imageUrl;
	
	public Client() {
		this.addRole(Role.CLIENT);
	}

	public Client(Long id, String name, String email, String cpfOrCnpj, ClientType type, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpfOrCnpj = cpfOrCnpj;
		this.type = (type == null) ? null : type.getCod();
		this.password = password;
		this.addRole(Role.CLIENT);
	}
	
	public ClientType getType() {
		return ClientType.toEnum(type);
	}
	
	public void SetType(ClientType type) {
		this.type = type.getCod();
	}
	
	public Set<Role> getRoles() {
		return this.roles.stream().map(x -> Role.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void addRole(Role role) {
		this.roles.add(role.getCod());
	}
}
