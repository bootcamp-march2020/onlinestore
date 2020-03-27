package com.movie.onlinestore.controllers;


import com.movie.onlinestore.UrlConstants;
import com.movie.onlinestore.model.*;
import com.movie.onlinestore.repository.MovieInventoryRepository;
import com.movie.onlinestore.repository.MovieRepository;
import com.movie.onlinestore.repository.OrderItemRepository;
import com.movie.onlinestore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private MovieInventoryRepository movieInventoryRepository;


    @PostMapping(UrlConstants.URL_PATH_PLACE_ORDER)
    @ResponseBody
    public ResponseEntity<Response<String>> placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
        System.out.println(placeOrderRequest.getAddress());

        Order order = createOrder(placeOrderRequest.getAddress());
        StringBuilder missedMovies = new StringBuilder();

        List<OrderItem> orderItems = new ArrayList<>();
        Double totalCost = 0D;

        for (PlaceOrderRequest.CartItem cartItem : placeOrderRequest.getCartItemList()) {
            Movie movie = movieRepository.findByIdIfHasStock(cartItem.getMovieId());
            if (null == movie)
                missedMovies.append(cartItem.getMovieName());
        }

        if (!missedMovies.toString().isEmpty())
            return new ResponseEntity<Response<String>>(new Response<String>(HttpStatus.OK.value(),
                    "Some movies out of stock", missedMovies.toString()), HttpStatus.OK);

        Set<Long> movieIds = new HashSet<>();

        for (PlaceOrderRequest.CartItem cartItem : placeOrderRequest.getCartItemList()) {
            Optional<Movie> movieRecord = movieRepository.findById(cartItem.getMovieId());

            Integer numOfDays = cartItem.getNumberOfDays();

            Movie movie = movieRecord.get();

            Double costOfMovie = movie.calculateCost(numOfDays);
            OrderItem orderItem = createItem(order.getOid(), movie, numOfDays, costOfMovie);
            orderItems.add(orderItem);

            totalCost += costOfMovie;

            movieIds.add(movie.getMid());
        }

        //Success
        order.setOrderItems(orderItems);
        order.setTotalCost(totalCost);

        orderRepository.save(order);


        movieInventoryRepository.updateMovieInventory(movieIds);

        return new ResponseEntity<>(Response.success("Success"), HttpStatus.OK);
    }

    private Order createOrder(String address) {
        return orderRepository.save(new Order(1L, address, new Date()));
    }

    private OrderItem createItem(Long orderId, Movie movie, int numOfDays, Double cost) {
        return orderItemRepository.save(new OrderItem(orderId,
                movie, numOfDays, movie.getPricingCategory().getInitalCost(),
                movie.getPricingCategory().getAdditionalCost(),
                movie.getPricingCategory().getCutoffDays(), cost));
    }
}
