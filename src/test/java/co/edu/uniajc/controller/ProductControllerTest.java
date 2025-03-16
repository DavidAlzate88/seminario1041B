package co.edu.uniajc.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import co.edu.uniajc.model.Product;
import co.edu.uniajc.service.ProductService;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = Product.builder()
                .id(1L)
                .name("Test Product 1")
                .description("Test description 1")
                .price(10.0)
                .stock(100)
                .build();

        product2 = Product.builder()
                .id(2L)
                .name("Test Product 2")
                .description("Test description 2")
                .price(20.0)
                .stock(200)
                .build();
    }

    @Test
    void save_shouldReturnCreatedProduct() {
        when(productService.createProduct(product1)).thenReturn(product1);

        ResponseEntity<Product> response = productController.save(product1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product1, response.getBody());
        verify(productService, times(1)).createProduct(product1);
    }

    @Test
    void save_shouldReturnInternalServerErrorOnException() {
        when(productService.createProduct(product1)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<Product> response = productController.save(product1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(productService, times(1)).createProduct(product1);
    }

    @Test
    void getProducts_shouldReturnListOfProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        when(productService.findAll()).thenReturn(productList);

        ResponseEntity<List<Product>> response = productController.getProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productList, response.getBody());
        verify(productService, times(1)).findAll();
    }

    @Test
    void getProducts_shouldReturnInternalServerErrorOnException() {
        when(productService.findAll()).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<List<Product>> response = productController.getProducts();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(productService, times(1)).findAll();
    }

    @Test
    void getProductById_shouldReturnProductIfExists() {
        when(productService.findById(1L)).thenReturn(Optional.of(product1));

        ResponseEntity<Optional<Product>> response = productController.getProductById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(product1), response.getBody());
        verify(productService, times(1)).findById(1L);
    }

    @Test
    void getProductById_shouldReturnEmptyOptionalIfProductDoesNotExist() {
        when(productService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Optional<Product>> response = productController.getProductById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.empty(), response.getBody());
        verify(productService, times(1)).findById(1L);
    }

    @Test
    void getProductById_shouldReturnInternalServerErrorOnException() {
        when(productService.findById(1L)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<Optional<Product>> response = productController.getProductById(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Optional.empty(), response.getBody());
        verify(productService, times(1)).findById(1L);
    }
}