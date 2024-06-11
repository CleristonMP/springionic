package com.cmp.springionic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cmp.springionic.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	@Transactional(readOnly=true)
	List<State> findAllByOrderByName();
}
