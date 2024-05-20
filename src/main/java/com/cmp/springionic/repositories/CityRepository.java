package com.cmp.springionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmp.springionic.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
