package com.movie.onlinestore.model;

import java.util.List;

public class PlaceOrderRequest {
    private List<CartItem> cartItemList;
    private String address;

    public  PlaceOrderRequest() {

    }
    public PlaceOrderRequest(List<CartItem> cartItemList, String address) {
        this.cartItemList = cartItemList;
        this.address = address;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static class CartItem {
        private Long movieId;
        private String movieName;
        private Integer numberOfDays;

        public CartItem() {

        }

        public CartItem(Long movieId, String movieName, Integer numberOfDays) {
            this.movieId = movieId;
            this.movieName = movieName;
            this.numberOfDays = numberOfDays;
        }

        public Long getMovieId() {
            return movieId;
        }

        public void setMovieId(Long movieId) {
            this.movieId = movieId;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public Integer getNumberOfDays() {
            return numberOfDays;
        }

        public void setNumberOfDays(Integer numberOfDays) {
            this.numberOfDays = numberOfDays;
        }
    }
}
