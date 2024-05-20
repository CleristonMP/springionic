package com.cmp.springionic.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientType {

	INDIVIDUAL(1, "Pessoa Física"),
	LEGALENTITY(2, "Pessoa Jurídica");
	
	private int cod;
	private String description;
	
	public static ClientType toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for (ClientType x : ClientType.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
