package com.cmp.springionic.dto;

import java.io.Serializable;

import com.cmp.springionic.domain.State;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class StateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	
	public StateDTO(State entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}
}
