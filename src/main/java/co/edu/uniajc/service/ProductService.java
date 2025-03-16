package co.edu.uniajc.service;

import co.edu.uniajc.exception.ProductException;
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
            throw new ProductException("Error creating product", e);
        }
    }

    public List<Product> findAll() {
        try {
            return productRepository.findAll();
        } catch (org.springframework.dao.DataAccessException e) {
            throw new ProductException("Error retrieving products from database", e);
        } catch (Exception e) {
            throw new ProductException("Unexpected error retrieving products",e);
        }
    }

    public Optional<Product> findById(Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isEmpty()) {
                throw new ProductException("Product with id " + id + " not found");
            }

            return product;
        } catch (org.springframework.dao.DataAccessException e) {
            throw new ProductException("Error retrieving product from database", e);
        } catch (Exception e) {
            throw new ProductException("Unexpected error retrieving product with id " + id, e);
        }
    }
}
