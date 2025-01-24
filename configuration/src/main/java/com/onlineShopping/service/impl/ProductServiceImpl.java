package com.onlineShopping.service.impl;


import com.onlineShopping.model.Product;
import com.onlineShopping.repository.ProductRepository;
import com.onlineShopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    /**
     * @param product
     * @return
     */
    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * @return
     */
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * @param productId
     * @return
     */
    @Override
    public Boolean deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (!Objects.isNull(product)) {
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * @param product
     * @param image
     * @return
     */
    @Override
    public Product updateProduct(Product product, MultipartFile image) {
        Product dbProduct = productRepository.findById(product.getId()).orElse(null);
        String imageName;
        if (image.isEmpty()) {
            assert dbProduct != null;
            imageName = dbProduct.getImage();
        } else {
            imageName = image.getOriginalFilename();
        }
        assert dbProduct != null;
        dbProduct.setTitle(product.getTitle());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setCategory(product.getCategory());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setStock(product.getStock());
        dbProduct.setImage(imageName);
        dbProduct.setActive(product.getActive());
        dbProduct.setDiscount(product.getDiscount());

        // 5=100*(5/100); 100-5=95
        Double discount = product.getPrice() * (product.getDiscount() / 100.0);
        Double discountPrice = product.getPrice() - discount;
        dbProduct.setDiscountPrice(discountPrice);

        Product updateProduct = productRepository.save(dbProduct);
        if (!ObjectUtils.isEmpty(updateProduct)) {

            if (!image.isEmpty()) {

                try {
                    File saveFile = new ClassPathResource("static/img").getFile();

                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
                            + image.getOriginalFilename());
                    Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return product;
        }
        return null;
    }

    /**
     * @param category
     * @return
     */
    @Override
    public List<Product> getAllActiveProducts(String category) {
        List<Product> products = null;
        if (ObjectUtils.isEmpty(category)) {
            products = productRepository.findByIsActiveTrue();
        } else {
            products = productRepository.findByCategory(category);
        }

        return products;
    }

    /**
     * @param search
     * @return
     */
    @Override
    public List<Product> searchProduct(String search) {
        return productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(search, search);
    }

    /**
     * @param pageNo
     * @param pageSize
     * @param category
     * @return
     */
    @Override
    public Page<Product> getAllActiveProductPagination(Integer pageNo, Integer pageSize, String category) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> pageProduct = null;

        if (ObjectUtils.isEmpty(category)) {
            pageProduct = productRepository.findByIsActiveTrue(pageable);
        } else {
            pageProduct = productRepository.findByCategory(pageable, category);
        }
        return pageProduct;
    }

    /**
     * @param pageNo
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public Page<Product> searchProductPagination(Integer pageNo, Integer pageSize, String search) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(search, search, pageable);
    }

    /**
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<Product> getAllProductsPagination(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.findAll(pageable);
    }

    /**
     * @param pageNo
     * @param pageSize
     * @param category
     * @param search
     * @return
     */
    @Override
    public Page<Product> searchProductsPagination(Integer pageNo, Integer pageSize, String category, String search) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(search, search, pageable);
    }

    /**
     * @param pageNo
     * @param pageSize
     * @param category
     * @param search
     * @return
     */
    @Override
    public Page<Product> searchActiveProductPagination(Integer pageNo, Integer pageSize, String category, String search) {
        Page<Product> pageProduct = null;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        pageProduct = productRepository.findByisActiveTrueAndTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(search,
                search, pageable);

//		if (ObjectUtils.isEmpty(category)) {
//			pageProduct = productRepository.findByIsActiveTrue(pageable);
//		} else {
//			pageProduct = productRepository.findByCategory(pageable, category);
//		}
        return pageProduct;
    }


}
