package com.cmp.springionic.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.cmp.springionic.domain.PaymentByBankSlip;

@Service
public class BankSlipService {

	public void fillInBankSlipPayment(PaymentByBankSlip payment, Date orderInstant) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(orderInstant);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		payment.setDueDate(cal.getTime());
	}
}
