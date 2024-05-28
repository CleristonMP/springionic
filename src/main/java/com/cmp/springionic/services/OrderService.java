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
	private ProductService productService;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private EmailService emailService;
	
	public Order findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName()));
	}
	
	@Transactional
	public Order insert(Order obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.setClient(clientService.findById(obj.getClient().getId()));
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
			oi.setProduct(productService.findById(oi.getProduct().getId()));
			oi.setPrice(oi.getProduct().getPrice());
			oi.setOrder(obj);
		}
		orderItemRepository.saveAll(obj.getItems());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
}
