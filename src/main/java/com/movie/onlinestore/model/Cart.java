package com.movie.onlinestore.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> cartItemList;
    private Double totalCost;

    public Cart(){
        this.cartItemList = new ArrayList<CartItem>();
        this.totalCost = 0D;
    }

    public void addMovieToCart(Movie movie,Integer numberOfDays){
        CartItem cartItem = new CartItem(movie,numberOfDays);
        this.cartItemList.add(cartItem);
        this.totalCost += cartItem.getCost();
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public Double getTotalCost() {
        return totalCost;
    }

}
