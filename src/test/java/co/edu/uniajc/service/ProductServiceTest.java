package co.edu.uniajc.service;

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

import co.edu.uniajc.model.Product;
import co.edu.uniajc.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

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
    void createProduct_shouldReturnSavedProduct() {
        when(productRepository.save(product1)).thenReturn(product1);

        Product result = productService.createProduct(product1);

        assertEquals(product1, result);
        verify(productRepository, times(1)).save(product1);
    }

    @Test
    void findAll_shouldReturnListOfProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnProductIfExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        Optional<Product> result = productService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(product1, result.get());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldReturnEmptyOptionalIfProductDoesNotExist() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Product> result = productService.findById(1L);

        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(1L);
    }
}