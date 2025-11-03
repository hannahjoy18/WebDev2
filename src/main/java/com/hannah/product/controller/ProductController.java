package com.hannah.product.controller;


import com.hannah.product.dto.ProductDTO;
import com.hannah.product.model.Product;
import com.hannah.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    // Constructor
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get all products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    // Add new product
    @PostMapping("/products")
    public Product newProduct(@Valid @RequestBody ProductDTO product) {
        return productService.save(product);
    }

    // Update existing product
    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable int id, @Valid @RequestBody ProductDTO product) {
        Product existingProduct = productService.findById(id);
        if (existingProduct == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found.");
        }
        return productService.updateProduct(existingProduct, product);
    }

    // Delete product by ID
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable int id) {
        if (productService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found.");
        }
        productService.deleteProduct(id);
    }
}
