package com.onlineShopping.repository;


import com.onlineShopping.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Boolean existsByName(String name);

    public List<Category> findByIsActiveTrue();
}
