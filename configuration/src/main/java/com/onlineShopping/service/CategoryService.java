package com.onlineShopping.service;

import com.onlineShopping.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    public Category saveCategory(Category category);

    public Boolean existCategory(String name);

    public List<Category> getAllCategory();

    public Boolean deleteCategory(Long id);

    public Category getCategoryById(Long id);

    public List<Category> getActiveCategory();

    public Page<Category> getAllCategoryPagination(Integer pageNo, Integer pageSize);
}
