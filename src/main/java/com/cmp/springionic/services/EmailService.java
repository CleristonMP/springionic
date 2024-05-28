package com.cmp.springionic.services;

import org.springframework.mail.SimpleMailMessage;

import com.cmp.springionic.domain.Order;

public interface EmailService {

	void sendOrderConfirmationEmail(Order obj);
	
	void sendEmail(SimpleMailMessage msg);
}
