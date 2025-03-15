package co.edu.uniajc.service;

import co.edu.uniajc.model.Product;
import co.edu.uniajc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Error creating product", e);
        }
    }

    public List<Product> findAll() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving products", e);
        }
    }

    public Optional<Product> findById(Long id) {
        try {
            return productRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving product by ID", e);
        }
    }
}
