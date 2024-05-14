package com.cmp.springionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmp.springionic.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
