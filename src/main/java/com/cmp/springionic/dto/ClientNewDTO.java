package com.cmp.springionic.dto;

import java.io.Serializable;

import com.cmp.springionic.domain.Client;
import com.cmp.springionic.services.validation.ClientInsert;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@ClientInsert
public class ClientNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Size(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String name;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message = "Email inválido")
	private String email;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String cpfOrCnpj;
	private Integer type;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String publicPlace;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String number;
	
	private String complement;
	private String district;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String zipCode;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String phone1;
	private String phone2;
	private String phone3;
	
	private Long cityId;
	
	public ClientNewDTO(Client obj) {
		this.id = obj.getId();
	}
}
