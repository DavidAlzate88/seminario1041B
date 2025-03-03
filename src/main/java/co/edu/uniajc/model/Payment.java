package co.edu.uniajc.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "paymentCode")
    private String paymentCode;

    @Column(name = "amount")
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
