package com.cmp.springionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmp.springionic.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
