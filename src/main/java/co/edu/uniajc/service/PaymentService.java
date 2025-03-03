package co.edu.uniajc.service;

import co.edu.uniajc.model.Payment;
import co.edu.uniajc.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(Payment payment) {
        if (paymentRepository.findByPaymentCode(payment.getPaymentCode()) != null) {
            throw new RuntimeException("Ya existe un pago con el codigo: " + payment.getPaymentCode());
        }

        return paymentRepository.save(payment);
    }
}
