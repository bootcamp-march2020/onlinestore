package com.movie.onlinestore.model;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String,Object> getMovie() {

        Map<String,Object> movieDetails = new HashMap<>();
        movieDetails.put("id", movie.getMid());
        movieDetails.put("title", movie.getTitle());

        return  movieDetails;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public String getPriceCategoryName() {
        return movie.getPricingCategory().getName();
    }

    public String getInitialCostString () {
        return movie.getPricingCategory().getInitialCostString();
    }

    public String getAdditionalCostString() {
        return movie.getPricingCategory().getAdditionalCostString();
    }
}
