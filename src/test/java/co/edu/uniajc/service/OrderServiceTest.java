package co.edu.uniajc.service;

import co.edu.uniajc.exception.OrderException;
import co.edu.uniajc.model.Order;
import co.edu.uniajc.model.Product;
import co.edu.uniajc.model.User;
import co.edu.uniajc.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        User testUser = User.builder().id(1L).email("testUser@test").build();
        Product testProduct = Product.builder().id(1L).name("Test Product").build();
        List<Product> testProducts = new ArrayList<>();
        testProducts.add(testProduct);

        testOrder = Order.builder()
                .id(1L)
                .total(BigDecimal.TEN)
                .shipmentAddress("Test Address")
                .products(testProducts)
                .user(testUser)
                .build();
    }

    @Test
    void createOrderSuccess() {
        when(orderRepository.save(testOrder)).thenReturn(testOrder);

        Order createdOrder = orderService.createOrder(testOrder);

        assertEquals(testOrder, createdOrder);
        verify(orderRepository, times(1)).save(testOrder);
    }

    @Test
    void createOrderFailure() {
        when(orderRepository.save(testOrder)).thenThrow(new RuntimeException("Database error"));

        assertThrows(OrderException.class, () -> orderService.createOrder(testOrder));
        verify(orderRepository, times(1)).save(testOrder);
    }

    @Test
    void findByIdSuccess() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        Optional<Order> foundOrder = orderService.findById(1L);

        assertTrue(foundOrder.isPresent());
        assertEquals(testOrder, foundOrder.get());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderException.class, () -> orderService.findById(1L));
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdDataAccessException() {
        when(orderRepository.findById(1L)).thenThrow(new DataAccessResourceFailureException("Database connection failed"));

        assertThrows(OrderException.class, () -> orderService.findById(1L));
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdUnexpectedException() {
        when(orderRepository.findById(1L)).thenThrow(new NullPointerException("Unexpected error"));

        assertThrows(OrderException.class, () -> orderService.findById(1L));
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void findAllSuccess() {
        List<Order> orderList = Arrays.asList(testOrder, Order.builder().build());
        when(orderRepository.findAll()).thenReturn(orderList);

        List<Order> foundOrders = orderService.findAll();

        assertEquals(orderList, foundOrders);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void findAllDataAccessException() {
        when(orderRepository.findAll()).thenThrow(new DataAccessResourceFailureException("Database connection failed"));

        assertThrows(OrderException.class, () -> orderService.findAll());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void findAllUnexpectedException() {
        when(orderRepository.findAll()).thenThrow(new NullPointerException("Unexpected error"));
        assertThrows(OrderException.class, () -> orderService.findAll());
        verify(orderRepository, times(1)).findAll();
    }
}