package com.movie.onlinestore.controllers;

import com.movie.onlinestore.PlaceOrderRequestBuilder;
import com.movie.onlinestore.model.PlaceOrderRequest;
import com.movie.onlinestore.model.Response;
import com.movie.onlinestore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderService orderService;

    @Test
    public void givenInvalidAddressShouldReturnHTTPError422() {

        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();
        placeOrderRequest.setCartItemList(new ArrayList<>());

        Mockito.when(orderService.isAddressValid(placeOrderRequest)).thenCallRealMethod();

        ResponseEntity responseResponseEntity =
                orderController.placeOrder(placeOrderRequest);

        Response<String> response = (Response<String>) responseResponseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatusCode());
        assertEquals("Invalid address provided", response.getMessage());
    }

    @Test
    public void givenZeroCartItemsShouldReturnHTTPError422(){
        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequestBuilder().setAddress("Valid address").build();

        Mockito.when(orderService.isAddressValid(placeOrderRequest)).thenCallRealMethod();
        Mockito.when(orderService.isCartListValid(placeOrderRequest)).thenCallRealMethod();

        ResponseEntity<Response<String>> responseResponseEntity =
                orderController.placeOrder(placeOrderRequest);

        Response<String> response = responseResponseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatusCode());
        assertEquals("Cart is empty", response.getMessage());
    }

}