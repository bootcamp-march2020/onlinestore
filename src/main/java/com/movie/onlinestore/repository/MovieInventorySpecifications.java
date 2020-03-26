package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.MovieInventory;
import org.springframework.data.jpa.domain.Specification;

public class MovieInventorySpecifications {
    public static Specification<MovieInventory> hasStock() {
        return (movieInventory, cq, cb) -> cb.greaterThan(movieInventory.get("availableCount"),0);
    }
}
