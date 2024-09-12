package org.example.ctactddminiproject.repository;


import org.example.ctactddminiproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
