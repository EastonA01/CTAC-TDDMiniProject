package org.example.ctactddminiproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ctactddminiproject.controller.OrderController;
import org.example.ctactddminiproject.entity.Order;
import org.example.ctactddminiproject.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Optional;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setCustomerName("John Doe");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("123 Main St");
        order.setTotal(99.99);
    }

    // Test for creating an order (POST /orders)
    @Test
    void shouldCreateOrder() throws Exception {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value(order.getCustomerName()))
                .andExpect(jsonPath("$.orderDate").value(order.getOrderDate().toString()))
                .andExpect(jsonPath("$.shippingAddress").value(order.getShippingAddress()))
                .andExpect(jsonPath("$.total").value(order.getTotal()));

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    // Test for fetching an order by ID (GET /orders/{id})
    @Test
    void shouldGetOrderById() throws Exception {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value(order.getCustomerName()))
                .andExpect(jsonPath("$.orderDate").value(order.getOrderDate().toString()))
                .andExpect(jsonPath("$.shippingAddress").value(order.getShippingAddress()))
                .andExpect(jsonPath("$.total").value(order.getTotal()));

        verify(orderRepository, times(1)).findById(1L);
    }

    // Test for updating an order by ID (PUT /orders/{id})
    @Test
    void shouldUpdateOrderById() throws Exception {
        // Mock the repository to return the original order for findById()
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Mock the repository to return the updated order for save()
        Order updatedOrder = new Order();
        updatedOrder.setId(1L);
        updatedOrder.setCustomerName("Jane Doe");
        updatedOrder.setOrderDate(LocalDate.now());
        updatedOrder.setShippingAddress("456 Oak St");
        updatedOrder.setTotal(199.99);

        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder); // Updated order returned

        mockMvc.perform(put("/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Jane Doe")) // Expect updated values
                .andExpect(jsonPath("$.orderDate").value(updatedOrder.getOrderDate().toString()))
                .andExpect(jsonPath("$.shippingAddress").value("456 Oak St"))
                .andExpect(jsonPath("$.total").value(199.99));

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(any(Order.class)); // Ensure save is called
    }

    // Test for deleting an order by ID (DELETE /orders/{id})
    @Test
    void shouldDeleteOrderById() throws Exception {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        mockMvc.perform(delete("/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    // Test for trying to fetch a non-existent order (GET /orders/{id})
    @Test
    void shouldReturnNotFoundForNonExistentOrder() throws Exception {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(orderRepository, times(1)).findById(1L);
    }

    // Test for trying to delete a non-existent order (DELETE /orders/{id})
    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentOrder() throws Exception {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(orderRepository, times(1)).findById(1L);
    }
}