package com.movie.onlinestore;

import com.movie.onlinestore.model.Movie;
import com.movie.onlinestore.model.PricingCategory;

public class MovieBuilder {
    private Long mid;
    private PricingCategory pricingCategory;


    public MovieBuilder setMid(Long mid) {
        this.mid = mid;
        return this;
    }

    public MovieBuilder setPricingCategory(PricingCategory pricingCategory) {
        this.pricingCategory = pricingCategory;
        return this;
    }

    public Movie build(){
        Movie movie = new Movie();
        movie.setMid(this.mid);
        movie.setPricingCategory(this.pricingCategory);
        return movie;
    }
}
