package com.cmp.springionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmp.springionic.domain.OrderItem;
import com.cmp.springionic.domain.OrderItemPK;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
