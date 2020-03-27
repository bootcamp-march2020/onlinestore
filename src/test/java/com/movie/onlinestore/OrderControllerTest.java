package com.movie.onlinestore;

import com.movie.onlinestore.controllers.OrderController;
import com.movie.onlinestore.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class OrderControllerTest {
    @InjectMocks
    OrderController orderController;

    @Mock
    OrderRepository orderRepository;
//
//    @Test
//    public void testOrderConfirmation () {
//        String expected = "Success";
//
//        String response = orderController.placeOrder();
//
//        assertEquals(expected, response);
//    }
}
