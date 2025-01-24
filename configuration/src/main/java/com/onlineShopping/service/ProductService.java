package com.onlineShopping.service;

import com.onlineShopping.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public Product saveProduct(Product product);
//
   public List<Product> getAllProducts();
//
    public Boolean deleteProduct(Long productId);
//
    public Product getProductById(Long productId);
//
    public Product updateProduct(Product product, MultipartFile file);
//
    public List<Product> getAllActiveProducts(String category);
//
    public List<Product> searchProduct(String search);
//
    public Page<Product> getAllActiveProductPagination(Integer pageNo, Integer pageSize, String category);
//
    public Page<Product> searchProductPagination(Integer pageNo, Integer pageSize, String search);
//
    public Page<Product> getAllProductsPagination(Integer pageNo, Integer pageSize);
//
     public Page<Product> searchProductsPagination(Integer pageNo, Integer pageSize, String category, String search);
//
    public Page<Product> searchActiveProductPagination(Integer pageNo, Integer pageSize, String category, String search);



}
