package co.edu.uniajc.service;

import co.edu.uniajc.exception.OrderException;
import co.edu.uniajc.model.Order;
import co.edu.uniajc.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new OrderException("Error creating order", e);
        }
    }

    public Optional<Order> findById(Long id) {
        try {
            Optional<Order> order = orderRepository.findById(id);
             if (order.isEmpty()) {
                 throw new OrderException("Order with id " + id + "  not found");
             }

            return order;
        } catch (DataAccessException e) {
            throw new OrderException("Error retrieving order from database", e);
        } catch (Exception e) {
            throw new OrderException("Unexpected error retrieving order with id " + id, e);
        }
    }

    public List<Order> findAll() {
        try {
            return orderRepository.findAll();
        } catch (DataAccessException e) {
            throw new OrderException("Error retrieving orders from database", e);
        } catch (Exception e) {
            throw new OrderException("Unexpected error retrieving orders" ,e);
        }
    }
}
