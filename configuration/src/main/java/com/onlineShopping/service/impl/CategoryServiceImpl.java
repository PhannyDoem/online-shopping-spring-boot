package com.onlineShopping.service.impl;

import com.onlineShopping.model.Category;
import com.onlineShopping.repository.CategoryRepository;
import com.onlineShopping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * @param category
     * @return
     */
    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * @param name
     * @return
     */
    @Override
    public Boolean existCategory(String name) {
        return categoryRepository.existsByName(name);
    }

    /**
     * @return
     */
    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Boolean deleteCategory(Long id) {
        Category category = categoryRepository.findById((long) id).orElse(null);
        if (!ObjectUtils.isEmpty(category)) {
            categoryRepository.delete(category);
            return true;
        }
        return false;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    /**
     * @return
     */
    @Override
    public List<Category> getActiveCategory() {
        return categoryRepository.findByIsActiveTrue();
    }

    /**
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<Category> getAllCategoryPagination(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return categoryRepository.findAll(pageable);
    }
}
