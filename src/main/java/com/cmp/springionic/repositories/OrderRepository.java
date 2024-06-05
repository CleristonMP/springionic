package com.cmp.springionic.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cmp.springionic.domain.Client;
import com.cmp.springionic.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Transactional(readOnly = true)
	Page<Order> findByClient(Client client, Pageable pageable);
}
