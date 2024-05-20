package com.cmp.springionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmp.springionic.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
