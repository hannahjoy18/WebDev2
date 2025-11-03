package com.hannah.product.service;

import com.hannah.product.dto.ProductDTO;
import com.hannah.product.model.Product;
import com.hannah.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get all products
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Find product by ID
    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    // Save new product
    public Product save(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setStock(dto.getStock());
        product.setUnit(dto.getUnit());
        product.setPrice(dto.getPrice());
        return productRepository.save(product);
    }

    // Update existing product
    public Product updateProduct(Product existing, ProductDTO dto) {
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setStock(dto.getStock());
        existing.setUnit(dto.getUnit());
        existing.setPrice(dto.getPrice());
        return productRepository.save(existing);
    }

    // Delete product by ID
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
