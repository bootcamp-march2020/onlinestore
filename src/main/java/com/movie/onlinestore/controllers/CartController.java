package com.movie.onlinestore.controllers;

import com.movie.onlinestore.model.Cart;
import com.movie.onlinestore.model.Movie;
import com.movie.onlinestore.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;


@RestController
public class CartController {
    @Autowired
    MovieRepository movieRepository;

    @RequestMapping( value = "/api/checkout", method = RequestMethod.POST)
    public Cart checkoutMovies(@RequestBody Map<String, Object>[] payload) {
        Cart cart = new Cart();
        for(Map<String,Object> object : payload){
            Optional<Movie> movieRecord = movieRepository.findById(new Long((Integer) object.get("movieId")));
            Integer numberOfDays = (Integer) object.get("numberOfDays");
            movieRecord.ifPresent(movie -> cart.addMovieToCart(movie, numberOfDays));
        }
        return cart;
    }
}
