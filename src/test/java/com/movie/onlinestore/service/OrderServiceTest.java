package com.movie.onlinestore.service;

import com.movie.onlinestore.PlaceOrderRequestBuilder;
import com.movie.onlinestore.model.PlaceOrderRequest;
import com.movie.onlinestore.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    MovieRepository movieRepository;

    @Test
    void givenOutOfStockCartItemsShouldReturnOutOfStockMoviesIds(){

        PlaceOrderRequest.CartItem cartItem = new PlaceOrderRequest.CartItem();
        cartItem.setMovieId(1L);

        Mockito.when(movieRepository.findByIdIfHasStock(1L)).thenReturn(null);

        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequestBuilder()
                .setAddress("Address").setCartItemList(Arrays.asList(cartItem)).build();

        List<Long> outOfStockMoviesIds = orderService.checkForMissingCartItems(placeOrderRequest);

        assertEquals(1L,outOfStockMoviesIds.get(0));

    }
}