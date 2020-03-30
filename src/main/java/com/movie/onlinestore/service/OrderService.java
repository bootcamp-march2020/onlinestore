package com.movie.onlinestore.service;

import com.movie.onlinestore.model.*;
import com.movie.onlinestore.repository.MovieInventoryRepository;
import com.movie.onlinestore.repository.MovieRepository;
import com.movie.onlinestore.repository.OrderItemRepository;
import com.movie.onlinestore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private MovieInventoryRepository movieInventoryRepository;

    public boolean isAddressValid(PlaceOrderRequest placeOrderRequest) {
        String address = placeOrderRequest.getAddress();
        return null != address && !address.isEmpty();
    }

    public boolean isCartListValid(PlaceOrderRequest placeOrderRequest) {
        List<PlaceOrderRequest.CartItem> cartItemList = placeOrderRequest.getCartItemList();
        return null != cartItemList && !cartItemList.isEmpty();
    }

    public List<Long> checkForMissingCartItems(PlaceOrderRequest placeOrderRequest){
        List<Long> outOfStockMovies = new ArrayList<>();

        for (PlaceOrderRequest.CartItem cartItem : placeOrderRequest.getCartItemList()) {
            Movie movie = movieRepository.findByIdIfHasStock(cartItem.getMovieId());
            if (null == movie)
                outOfStockMovies.add(cartItem.getMovieId());
        }

        return outOfStockMovies;
    }

    public void placeOrder(PlaceOrderRequest placeOrderRequest, String customerId){

        Order order = createOrder(placeOrderRequest.getAddress(), customerId);

        List<OrderItem> orderItems = new ArrayList<>();
        Double totalCost = 0D;

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
    }

    private Order createOrder(String address, String customerId) {
        return orderRepository.save(new Order(customerId, address, new Date()));
    }

    private OrderItem createItem(Long orderId, Movie movie, int numOfDays, Double cost) {
        return orderItemRepository.save(new OrderItem(orderId,
                movie, numOfDays, movie.getPricingCategory().getInitalCost(),
                movie.getPricingCategory().getAdditionalCost(),
                movie.getPricingCategory().getCutoffDays(), cost));
    }
}
