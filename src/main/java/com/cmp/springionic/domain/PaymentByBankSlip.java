package com.cmp.springionic.domain;

import java.util.Date;

import com.cmp.springionic.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonTypeName("paymentByBankSlip")
public class PaymentByBankSlip extends Payment {
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dueDate;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date paymentDate;
	
	public PaymentByBankSlip(Long id, PaymentStatus status, Order order, Date dueDate, Date paymentDate) {
		super(id, status, order);
		this.paymentDate = paymentDate;
		this.dueDate = dueDate;
	}
}
