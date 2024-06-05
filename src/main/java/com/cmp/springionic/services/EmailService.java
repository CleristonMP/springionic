package com.cmp.springionic.services;

import org.springframework.mail.SimpleMailMessage;

import com.cmp.springionic.domain.Client;
import com.cmp.springionic.domain.Order;

import jakarta.mail.internet.MimeMessage;

public interface EmailService {

	void sendOrderConfirmationEmail(Order obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Order obj);
	
	void sendHtmlEmail(MimeMessage msg);

	void sendNewPasswordEmail(Client client, String newPass);
}
