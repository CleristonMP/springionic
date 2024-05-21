package com.cmp.springionic.dto;

import java.io.Serializable;

import com.cmp.springionic.domain.Client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ClientNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	private String email;
	private String cpfOrCnpj;
	private Integer type;
	
	private String publicPlace;
	private String number;
	private String complement;
	private String district;
	private String zipCode;
	
	private String phone1;
	private String phone2;
	private String phone3;
	
	private Long cityId;
	
	public ClientNewDTO(Client obj) {
		this.id = obj.getId();
	}
}
