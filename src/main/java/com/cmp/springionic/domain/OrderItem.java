package com.cmp.springionic.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_order_item")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OrderItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@EmbeddedId
	private OrderItemPK id = new OrderItemPK();

	private Double discount;
	private Integer quantity;
	private Double price;
	
	public OrderItem(Order order, Product product, Double discount, Integer quantity, Double price) {
		super();
		id.setOrder(order);
		id.setProduct(product);
		this.discount = discount;
		this.quantity = quantity;
		this.price = price;
	}
	
	public double getSubTotal() {
		return (this.price - this.discount) * this.quantity;
	}
	
	@JsonIgnore
	public Order getOrder() {
		return id.getOrder();
	}
	
	public void setOrder(Order order) {
		this.id.setOrder(order);
	}
	
	public Product getProduct() {
		return id.getProduct();
	}
	
	public void setProduct(Product product) {
		this.id.setProduct(product);
	}

	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		StringBuilder builder = new StringBuilder();
		builder.append(this.getProduct().getName());
		builder.append(", Qtd: ");
		builder.append(this.getQuantity());
		builder.append(", Preço unitário: ");
		builder.append(nf.format(this.getPrice()));
		builder.append(", Subtotal: ");
		builder.append(nf.format(this.getSubTotal()));
		builder.append("\n");
		return builder.toString();
	}
}
