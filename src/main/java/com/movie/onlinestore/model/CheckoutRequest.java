package com.movie.onlinestore.model;

public class CheckoutRequest {
    private Long movieId;
    private Integer numberOfDays;

    public CheckoutRequest() {

    }

    public CheckoutRequest(Long movieId, Integer numberOfDays) {
        this.movieId = movieId;
        this.numberOfDays = numberOfDays;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
}
