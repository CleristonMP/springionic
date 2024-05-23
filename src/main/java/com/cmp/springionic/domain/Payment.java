package com.cmp.springionic.domain;

import java.io.Serializable;
import java.util.Objects;

import com.cmp.springionic.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_payment")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Payment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	private Integer status;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "order_id")
	@MapsId
	private Order order;

	public Payment(Long id, PaymentStatus status, Order order) {
		super();
		this.id = id;
		this.status = (status == null) ? null : status.getCod();
		this.order = order;
	}
	
	public PaymentStatus getStatus() {
		return PaymentStatus.toEnum(status);
	}
	
	public void setStatus(PaymentStatus status) {
		this.status = status.getCod();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		return Objects.equals(id, other.id);
	}
}
