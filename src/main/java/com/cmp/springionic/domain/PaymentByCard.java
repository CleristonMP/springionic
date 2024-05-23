package com.cmp.springionic.domain;

import com.cmp.springionic.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonTypeName("paymentByCard")
public class PaymentByCard extends Payment {
	private static final long serialVersionUID = 1L;

	private Integer numberOfInstallments;

	public PaymentByCard(Long id, PaymentStatus status, Order order, Integer numberOfInstallments) {
		super(id, status, order);
		this.numberOfInstallments = numberOfInstallments;
	}	
}
