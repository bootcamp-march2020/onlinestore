package com.movie.onlinestore.model;

public class CartItem {

    private Movie movie;
    private Integer numberOfDays;
    private Double cost;

    public CartItem(Movie movie,Integer numberOfDays){
        this.movie = movie;
        this.numberOfDays = numberOfDays;
        this.cost = movie.calculateCost(numberOfDays);
    }

    public Double getCost(){
        return cost;
    }

    public Movie getMovie() {
        return movie;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }
}
