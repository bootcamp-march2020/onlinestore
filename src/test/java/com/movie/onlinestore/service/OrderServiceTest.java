package com.movie.onlinestore.service;

import com.movie.onlinestore.MovieBuilder;
import com.movie.onlinestore.PlaceOrderRequestBuilder;
import com.movie.onlinestore.model.*;
import com.movie.onlinestore.repository.MovieInventoryRepository;
import com.movie.onlinestore.repository.MovieRepository;
import com.movie.onlinestore.repository.OrderItemRepository;
import com.movie.onlinestore.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieInventoryRepository movieInventoryRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private MailService mailService;

    @Test
    void givenOutOfStockCartItemsShouldReturnOutOfStockMoviesIds() {

        PlaceOrderRequest.CartItem cartItem = new PlaceOrderRequest.CartItem();
        cartItem.setMovieId(1L);

        Mockito.when(movieRepository.findByIdIfHasStock(1L)).thenReturn(null);

        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequestBuilder()
                .setAddress("Address").setCartItemList(Arrays.asList(cartItem)).build();

        List<Long> outOfStockMoviesIds = orderService.checkForMissingCartItems(placeOrderRequest);

        assertEquals(1L, outOfStockMoviesIds.get(0));

    }

    @Test
    void givenPlaceOrderIsSuccessShouldSendInvoiceMail() {

        User user = getUser();

        String address = "Valid address";

        PlaceOrderRequest.CartItem cartItem = new PlaceOrderRequest.CartItem();
        cartItem.setMovieId(1L);
        cartItem.setNumberOfDays(2);

        Movie movie = new MovieBuilder().setMid(2L).setDefaultPrincingCategory().build();
        Mockito.when(movieRepository.findById(cartItem.getMovieId())).thenReturn(
                Optional.of(movie)
        );

        Order order = new Order(user.getUserId(), address, new Date());

        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
        Mockito.when(orderItemRepository.save(Mockito.any())).thenReturn(getSampleOrderItem(movie));
        Mockito.doNothing().when(movieInventoryRepository).updateMovieInventory(Mockito.any());

        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequestBuilder()
                .setAddress(address).setCartItemList(Arrays.asList(cartItem)).build();


        orderService.placeOrder(placeOrderRequest, user);

        Mockito.verify(mailService, Mockito.times(1)).sendInvoice(user, order);

    }

    private OrderItem getSampleOrderItem(Movie movie) {
        return new OrderItem(2L, movie, 20, 5D, 4D, 8, 50D);
    }

    private User getUser() {
        User user = new User();
        user.setUserId("1234");
        user.setEmail("sample@gmail.com");
        user.setName("User name");
        return user;
    }
}