package co.edu.uniajc.service;

import co.edu.uniajc.exception.PaymentException;
import co.edu.uniajc.model.Payment;
import co.edu.uniajc.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

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
    void createPaymentSuccess() {
        when(paymentRepository.save(testPayment)).thenReturn(testPayment);

        Payment createdPayment = paymentService.createPayment(testPayment);

        assertEquals(testPayment, createdPayment);
        verify(paymentRepository, times(1)).save(testPayment);
    }

    @Test
    void createPaymentFailure() {
        when(paymentRepository.save(testPayment)).thenThrow(new RuntimeException("Database error"));

        assertThrows(PaymentException.class, () -> paymentService.createPayment(testPayment));
        verify(paymentRepository, times(1)).save(testPayment);
    }

    @Test
    void findAllSuccess() {
        List<Payment> paymentList = Arrays.asList(testPayment, Payment.builder().build());
        when(paymentRepository.findAll()).thenReturn(paymentList);

        List<Payment> foundPayments = paymentService.findAll();

        assertEquals(paymentList, foundPayments);
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void findAllDataAccessException() {
        when(paymentRepository.findAll()).thenThrow(new DataAccessResourceFailureException("Database connection failed"));

        assertThrows(PaymentException.class, () -> paymentService.findAll());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void findAllUnexpectedException() {
        when(paymentRepository.findAll()).thenThrow(new NullPointerException("Unexpected error"));

        assertThrows(PaymentException.class, () -> paymentService.findAll());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void findByIdSuccess() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));

        Optional<Payment> foundPayment = paymentService.findById(1L);

        assertTrue(foundPayment.isPresent());
        assertEquals(testPayment, foundPayment.get());
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PaymentException.class, () -> paymentService.findById(1L));
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdDataAccessException() {
        when(paymentRepository.findById(1L)).thenThrow(new DataAccessResourceFailureException("Database connection failed"));

        assertThrows(PaymentException.class, () -> paymentService.findById(1L));
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdUnexpectedException() {
        when(paymentRepository.findById(1L)).thenThrow(new NullPointerException("Unexpected error"));

        assertThrows(PaymentException.class, () -> paymentService.findById(1L));
        verify(paymentRepository, times(1)).findById(1L);
    }
}