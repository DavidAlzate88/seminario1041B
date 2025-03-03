package co.edu.uniajc.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "shipmentAddress")
    private String shipmentAddress;

    @ManyToMany
    @Column(name = "product_id")
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
