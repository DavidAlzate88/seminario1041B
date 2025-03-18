package co.edu.uniajc.controller;

import co.edu.uniajc.model.Payment;
import co.edu.uniajc.service.PaymentService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private Payment testPayment;

    @BeforeEach
    void setUp() {
        testPayment = Payment.builder()
                .id(1L)
                .amount(BigDecimal.TEN)
                .paymentCode("Credit Card")
                .build();
    }

    @Test
    void createPayment_success() {
        when(paymentService.createPayment(testPayment)).thenReturn(testPayment);

        ResponseEntity<Payment> response = paymentController.createPayment(testPayment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testPayment, response.getBody());
        verify(paymentService, times(1)).createPayment(testPayment);
    }

    @Test
    void getPayments_success() {
        List<Payment> paymentList = Arrays.asList(testPayment, Payment.builder().build());
        when(paymentService.findAll()).thenReturn(paymentList);

        ResponseEntity<List<Payment>> response = paymentController.getPayments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentList, response.getBody());
        verify(paymentService, times(1)).findAll();
    }

    @Test
    void getPaymentById_success() {
        when(paymentService.findById(1L)).thenReturn(Optional.of(testPayment));

        ResponseEntity<Optional<Payment>> response = paymentController.getOrderById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(testPayment), response.getBody());
        verify(paymentService, times(1)).findById(1L);
    }

    @Test
    void getPaymentById_notFound() {
        when(paymentService.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Optional<Payment>> response = paymentController.getOrderById(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.empty(), response.getBody());
        verify(paymentService, times(1)).findById(2L);
    }
}