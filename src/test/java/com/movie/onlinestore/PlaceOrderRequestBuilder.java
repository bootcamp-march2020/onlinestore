package com.movie.onlinestore;

import com.movie.onlinestore.model.PlaceOrderRequest;

import java.util.List;

public class PlaceOrderRequestBuilder {
    private String address;
    private List<PlaceOrderRequest.CartItem> cartItemList;

    public PlaceOrderRequestBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public PlaceOrderRequestBuilder setCartItemList(List<PlaceOrderRequest.CartItem> cartItemList) {
        this.cartItemList = cartItemList;
        return this;
    }

    public PlaceOrderRequest build() {
        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();
        placeOrderRequest.setAddress(address);
        placeOrderRequest.setCartItemList(cartItemList);
        return placeOrderRequest;
    }
}