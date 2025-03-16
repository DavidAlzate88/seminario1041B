package co.edu.uniajc.service;

import co.edu.uniajc.exception.PaymentException;
import co.edu.uniajc.model.Payment;
import co.edu.uniajc.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(Payment payment) {
        try {
            return paymentRepository.save(payment);
        } catch (Exception e) {
            throw new PaymentException("Error creating payment", e);
        }
    }

    public List<Payment> findAll() {
        try {
            return paymentRepository.findAll();
        } catch (DataAccessException e) {
            throw new PaymentException("Error retrieving payments from database", e);
        } catch (Exception e) {
            throw new PaymentException("Unexpected error retrieving payments", e);
        }
    }

    public Optional<Payment> findById(Long id) {
        try {
            Optional<Payment> payment = paymentRepository.findById(id);

            if (payment.isEmpty()) {
                throw new PaymentException("Payment with id " + id + " not found");
            }
            return payment;
        } catch (DataAccessException e) {
            throw new PaymentException("Error retrieving payment from database", e);
        } catch (Exception e) {
            throw new PaymentException("Unexpected error retrieving payment with id " + id, e);
        }
    }
}
