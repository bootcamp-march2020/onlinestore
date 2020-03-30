package com.movie.onlinestore.model.response;

import com.movie.onlinestore.model.CartItem;
import com.movie.onlinestore.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class CartResponse {
    private List<CartItem> cartItemList;
    private Double totalCost;
    private List<Long> outOfStockMoviesIds;

    public CartResponse(){
        this.cartItemList = new ArrayList<CartItem>();
        this.totalCost = 0D;
        this.outOfStockMoviesIds = new ArrayList<>();
    }

    public void addMovieToCart(Movie movie, Integer numberOfDays){
        CartItem cartItem = new CartItem(movie,numberOfDays);
        this.cartItemList.add(cartItem);
        this.totalCost += cartItem.getCost();
    }

    public void addOutOfStockMovies(Long movieId){
        outOfStockMoviesIds.add(movieId);
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public List<Long> getOutOfStockMoviesIds() {
        return outOfStockMoviesIds;
    }
}
