package com.cmp.springionic.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmp.springionic.domain.Order;
import com.cmp.springionic.domain.OrderItem;
import com.cmp.springionic.domain.PaymentByBankSlip;
import com.cmp.springionic.domain.enums.PaymentStatus;
import com.cmp.springionic.repositories.OrderItemRepository;
import com.cmp.springionic.repositories.OrderRepository;
import com.cmp.springionic.repositories.PaymentRepository;
import com.cmp.springionic.repositories.ProductRepository;
import com.cmp.springionic.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private BankSlipService bankSlipService;
	
	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public Order findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName()));
	}
	
	@Transactional
	public Order insert(Order obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.getPayment().setStatus(PaymentStatus.PENDING);
		obj.getPayment().setOrder(obj);
		if (obj.getPayment() instanceof PaymentByBankSlip) {
			PaymentByBankSlip pay = (PaymentByBankSlip) obj.getPayment();
			bankSlipService.fillInBankSlipPayment(pay, obj.getInstant());
		}
		obj = repository.save(obj);
		paymentRepository.save(obj.getPayment());
		for (OrderItem oi : obj.getItems()) {
			oi.setDiscount(0.0);
			oi.setPrice(productRepository.getReferenceById(oi.getProduct().getId()).getPrice());
			oi.setOrder(obj);
		}
		orderItemRepository.saveAll(obj.getItems());
		return obj;
	}
}
