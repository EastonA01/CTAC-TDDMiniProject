package org.example.ctactddminiproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ctactddminiproject.controller.OrderController;
import org.example.ctactddminiproject.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.time.LocalDate;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderController orderService;

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

    // TODO: Write test methods for all routes
    @Test
    void shouldCreateOrder() throws Exception {
        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerName").value(order.getCustomerName()))
                .andExpect(jsonPath("$.orderDate").value(order.getOrderDate().toString()))
                .andExpect(jsonPath("$.shippingAddress").value(order.getShippingAddress()))
                .andExpect(jsonPath("$.total").value(order.getTotal()));
    }

    @Test
    void shouldFetchOrderById() throws Exception {
        when(orderService.getOrder(order.getId())).thenReturn(order);

        mockMvc.perform(get("/orders/{id}", order.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value(order.getCustomerName()))
                .andExpect(jsonPath("$.orderDate").value(order.getOrderDate().toString()))
                .andExpect(jsonPath("$.shippingAddress").value(order.getShippingAddress()))
                .andExpect(jsonPath("$.total").value(order.getTotal()));
    }

    @Test
    void shouldUpdateOrder() throws Exception {
        when(orderService.updateOrder(anyLong(), any(Order.class))).thenReturn(order);

        mockMvc.perform(put("/orders/{id}", order.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value(order.getCustomerName()))
                .andExpect(jsonPath("$.orderDate").value(order.getOrderDate().toString()))
                .andExpect(jsonPath("$.shippingAddress").value(order.getShippingAddress()))
                .andExpect(jsonPath("$.total").value(order.getTotal()));
    }

    @Test
    void shouldDeleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder(order.getId());

        mockMvc.perform(delete("/orders/{id}", order.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
