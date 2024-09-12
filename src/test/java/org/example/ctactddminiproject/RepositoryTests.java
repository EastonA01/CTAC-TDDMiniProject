package org.example.ctactddminiproject;

import static org.assertj.core.api.Assertions.assertThat;
import org.example.ctactddminiproject.entity.Order;
import org.example.ctactddminiproject.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
public class RepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        // Initialize the Order object before each test
        order = new Order();
        order.setCustomerName("John Doe");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("123 Main St");
        order.setTotal(99.99);
    }

    @Test
    void saveOrder() {
        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Retrieve the order from the repository
        Order foundOrder = orderRepository.findById(savedOrder.getId()).orElse(null);

        // Assertions
        assertThat(foundOrder).isNotNull();
        assertThat(foundOrder.getCustomerName()).isEqualTo(order.getCustomerName());
        assertThat(foundOrder.getOrderDate()).isEqualTo(order.getOrderDate());
        assertThat(foundOrder.getShippingAddress()).isEqualTo(order.getShippingAddress());
        assertThat(foundOrder.getTotal()).isEqualTo(order.getTotal());
    }

    @Test
    void getOrder() {
        // Disclaimer: This basically does the exact same thing as saveOrder()
        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Retrieve the order from the repository
        Order foundOrder = orderRepository.findById(savedOrder.getId()).orElse(null);

        // Assertions
        assertThat(foundOrder).isNotNull();
        assertThat(foundOrder.getCustomerName()).isEqualTo(order.getCustomerName());
        assertThat(foundOrder.getOrderDate()).isEqualTo(order.getOrderDate());
        assertThat(foundOrder.getShippingAddress()).isEqualTo(order.getShippingAddress());
        assertThat(foundOrder.getTotal()).isEqualTo(order.getTotal());
    }

    @Test
    void updateOrder(){
        Order savedOrder = orderRepository.save(order);
        Order foundOrder = orderRepository.findById(savedOrder.getId()).orElse(null);
        foundOrder.setCustomerName("Jane Doe");
        foundOrder.setOrderDate(LocalDate.now());
        foundOrder.setShippingAddress("12345 Main St");
        foundOrder.setTotal(200.98);
        orderRepository.save(foundOrder);
        Order updatedOrder = orderRepository.findById(savedOrder.getId()).orElse(null);
        assertThat(updatedOrder).isNotNull();
        assertThat(updatedOrder.getCustomerName()).isEqualTo(foundOrder.getCustomerName());
        assertThat(updatedOrder.getOrderDate()).isEqualTo(foundOrder.getOrderDate());
        assertThat(updatedOrder.getShippingAddress()).isEqualTo(foundOrder.getShippingAddress());
        assertThat(updatedOrder.getTotal()).isEqualTo(foundOrder.getTotal());
    }

    @Test
    void deleteOrder() {
        Order savedOrder = orderRepository.save(order);
        Order foundOrder = orderRepository.findById(savedOrder.getId()).orElse(null);
        orderRepository.delete(foundOrder);
        Order deletedOrder = orderRepository.findById(savedOrder.getId()).orElse(null);
        assertThat(deletedOrder).isNull();
    }
}
