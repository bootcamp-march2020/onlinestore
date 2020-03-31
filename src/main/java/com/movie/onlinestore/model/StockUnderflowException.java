package com.movie.onlinestore.model;

public class StockUnderflowException extends Exception {
    public StockUnderflowException(String message) {
        super(message);
    }
}
