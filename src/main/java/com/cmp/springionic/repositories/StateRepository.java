package com.cmp.springionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmp.springionic.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

}
