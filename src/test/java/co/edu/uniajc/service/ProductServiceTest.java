package co.edu.uniajc.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.edu.uniajc.exception.ProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.edu.uniajc.model.Product;
import co.edu.uniajc.repository.ProductRepository;
import org.springframework.dao.DataAccessException;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private static final String DATABASE_ERROR = "Database error";

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
    void createProductShouldReturnSavedProduct() {
        when(productRepository.save(product1)).thenReturn(product1);

        Product result = productService.createProduct(product1);

        assertEquals(product1, result);
        verify(productRepository, times(1)).save(product1);
    }

    @Test
    void createProductShouldThrowExceptionOnErrorCreating() {
        when(productRepository.save(product1)).thenThrow(new RuntimeException(DATABASE_ERROR));

        ProductException thrown = assertThrows(ProductException.class, () -> productService.createProduct(product1));
        assertEquals("Error creating product", thrown.getMessage());

        verify(productRepository, times(1)).save(product1);
    }

    @Test
    void createProductShouldThrowExceptionOnErrorUpdating() {
        Product productToUpdate = Product.builder()
                .id(1L)
                .name("Updated Product 1")
                .description("Updated description 1")
                .price(15.0)
                .stock(150)
                .build();
        when(productRepository.save(productToUpdate)).thenThrow(new RuntimeException(DATABASE_ERROR));

        ProductException thrown = assertThrows(ProductException.class, () -> productService.createProduct(productToUpdate));
        assertEquals("Error updating product", thrown.getMessage());

        verify(productRepository, times(1)).save(productToUpdate);
    }

    @Test
    void findAllShouldReturnListOfProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.findAll();

        assertEquals(productList, result);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findAllShouldThrowProductExceptionWhenRepositoryThrowsException() {
        when(productRepository.findAll()).thenThrow(new DataAccessException(DATABASE_ERROR) {});

        assertThrows(ProductException.class, () -> productService.findAll());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findAllShouldThrowExceptionOnError() {
        when(productRepository.findAll()).thenThrow(new ProductException("Internal server error"));

        assertThrows(ProductException.class, () -> productService.findAll());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findByIdShouldReturnProductWhenProductExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        Optional<Product> result = productService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(product1, result.get());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdShouldThrowProductNotFoundExceptionWhenProductDoesNotExist() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductException.class, () -> productService.findById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdShouldThrowProductExceptionWhenRepositoryThrowsException() {
        when(productRepository.findById(1L)).thenThrow(new DataAccessException(DATABASE_ERROR) {});

        assertThrows(ProductException.class, () -> productService.findById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void deleteProductShouldDeleteProductWhenProductExists() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        assertDoesNotThrow(() -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProductShouldThrowProductExceptionWhenProductDoesNotExist() {
        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProductException.class, () -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, never()).deleteById(1L);
    }

    @Test
    void deleteProductShouldThrowProductExceptionWhenRepositoryThrowsDataAccessException() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doThrow(new DataAccessException(DATABASE_ERROR) {}).when(productRepository).deleteById(1L);

        assertThrows(ProductException.class, () -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProductShouldThrowProductExceptionForOtherExceptions() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException("Unexpected")).when(productRepository).deleteById(1L);

        assertThrows(ProductException.class, () -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }
}

