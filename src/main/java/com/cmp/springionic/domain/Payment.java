package com.cmp.springionic.domain;

import java.io.Serializable;

import com.cmp.springionic.domain.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_payment")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public abstract class Payment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	private Integer status;
	
	@OneToOne
	@JoinColumn(name = "order_id")
	@MapsId
	private Order order;

	public Payment(Long id, PaymentStatus status, Order order) {
		super();
		this.id = id;
		this.status = status.getCod();
		this.order = order;
	}
	
	public PaymentStatus getStatus() {
		return PaymentStatus.toEnum(status);
	}
	
	public void setStatus(PaymentStatus status) {
		this.status = status.getCod();
	}
}
