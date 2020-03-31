package com.movie.onlinestore.controllers;

import com.movie.onlinestore.UrlConstants;
import com.movie.onlinestore.model.CheckoutRequest;
import com.movie.onlinestore.model.Movie;
import com.movie.onlinestore.model.Response;
import com.movie.onlinestore.model.response.CartResponse;
import com.movie.onlinestore.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CartController {
    @Autowired
    MovieRepository movieRepository;

    @RequestMapping(value = UrlConstants.URL_PATH_CART_CHECKOUT, method = RequestMethod.POST)
    public ResponseEntity<Response<CartResponse>> checkoutMovies(@RequestBody List<CheckoutRequest> checkoutRequests) {

        CartResponse cartResponse = new CartResponse();

        for (CheckoutRequest request : checkoutRequests) {
            Movie movieRecord = movieRepository.findByIdIfHasStock(request.getMovieId());
            if (null == movieRecord)
                cartResponse.addOutOfStockMovies(request.getMovieId());
            else
                cartResponse.addMovieToCart(movieRecord, request.getNumberOfDays());
        }
        if (cartResponse.getOutOfStockMoviesIds().isEmpty())
            return new ResponseEntity<>(Response.success(cartResponse), HttpStatus.OK);
        return new ResponseEntity<>(new Response<>(HttpStatus.ACCEPTED.value(),
                "Some movies are out of stock",cartResponse), HttpStatus.OK);

    }
}
