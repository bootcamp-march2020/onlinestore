package com.movie.onlinestore.model;

import org.springframework.http.HttpStatus;

public class Response<T> {

    private int statusCode;

    private String message;

    private T payload;

    public static <T> Response<T> success(T payLoad) {
        return new Response<>(HttpStatus.OK.value(), "Success", payLoad);
    }

    public Response(int statusCode, String message, T payload) {
        this.statusCode = statusCode;
        this.message = message;
        this.payload = payload;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getPayload() {
        return payload;
    }
}
