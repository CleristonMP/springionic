package com.cmp.springionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmp.springionic.domain.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
