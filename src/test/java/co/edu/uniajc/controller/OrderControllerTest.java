package co.edu.uniajc.controller;

import co.edu.uniajc.model.Order;
import co.edu.uniajc.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = Order.builder()
                .id(1L)
                .total(BigDecimal.TEN)
                .shipmentAddress("Test Address")
                .build();
    }

    @Test
    void createOrder_success() {
        when(orderService.createOrder(testOrder)).thenReturn(testOrder);

        ResponseEntity<Order> response = orderController.createOrder(testOrder);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testOrder, response.getBody());
        verify(orderService, times(1)).createOrder(testOrder);
    }

    @Test
    void createOrder_internalServerError() {
        when(orderService.createOrder(testOrder)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<Order> response = orderController.createOrder(testOrder);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderService, times(1)).createOrder(testOrder);
    }

    @Test
    void getOrders_success() {
        List<Order> orderList = Arrays.asList(testOrder, Order.builder().build());
        when(orderService.findAll()).thenReturn(orderList);

        ResponseEntity<List<Order>> response = orderController.getOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderList, response.getBody());
        verify(orderService, times(1)).findAll();
    }

    @Test
    void getOrders_internalServerError() {
        when(orderService.findAll()).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<List<Order>> response = orderController.getOrders();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderService, times(1)).findAll();
    }

    @Test
    void getOrderById_success() {
        when(orderService.findById(1L)).thenReturn(Optional.of(testOrder));

        ResponseEntity<Optional<Order>> response = orderController.getOrderById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(testOrder), response.getBody());
        verify(orderService, times(1)).findById(1L);
    }

    @Test
    void getOrderById_internalServerError() {
        when(orderService.findById(1L)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<Optional<Order>> response = orderController.getOrderById(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Optional.empty(), response.getBody());
        verify(orderService, times(1)).findById(1L);
    }
}