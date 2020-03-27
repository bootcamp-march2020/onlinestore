package com.movie.onlinestore.controllers;


import com.movie.onlinestore.UrlConstants;
import com.movie.onlinestore.model.*;
import com.movie.onlinestore.repository.MovieRepository;
import com.movie.onlinestore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(UrlConstants.URL_PATH_PLACE_ORDER)
    @ResponseBody
    public ResponseEntity<Response<String>> placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
        System.out.println(placeOrderRequest.getAddress());

        Order order = createOrder(placeOrderRequest.getAddress());
        StringBuilder missedMovies = new StringBuilder();
        Cart cart = new Cart();
        List<OrderItem> orderItems = new ArrayList<>();

        for (PlaceOrderRequest.CartItem cartItem : placeOrderRequest.getCartItemList()) {
            Optional<Movie> movieRecord = movieRepository.findById(cartItem.getMovieId());
            Integer numberOfDays = cartItem.getNumberOfDays();

            if (movieRecord.isPresent()) {
            } else {
                missedMovies.append(cartItem.getMovieName());
            }
        }

        return new ResponseEntity<>(Response.success("Success"), HttpStatus.OK);
    }

    public Order createOrder(String address){
        return orderRepository.save(new Order(1L, address,new Date()));
    }
}
