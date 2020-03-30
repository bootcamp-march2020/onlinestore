package com.movie.onlinestore.controllers;

import com.movie.onlinestore.model.CheckoutRequest;
import com.movie.onlinestore.model.Movie;
import com.movie.onlinestore.model.PricingCategory;
import com.movie.onlinestore.model.Response;
import com.movie.onlinestore.model.response.CartResponse;
import com.movie.onlinestore.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CartControllerTest {
    @InjectMocks
    CartController cartController;

    @Mock
    MovieRepository movieRepository;
    @Test
    public void checkoutTotalCostOfMovies() {

        List<CheckoutRequest> checkoutRequests = new ArrayList<>();
        checkoutRequests.add(new CheckoutRequest(1L,10));
        checkoutRequests.add(new CheckoutRequest(2L,5));

        Movie movie1 = new Movie(1L,"Iron Man","After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.","PG-13",2008,"https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg",new Date(),7.9,"Movie",new PricingCategory(1L,"Default",3D,5,1D));
        Movie movie2 = new Movie(2L,"Super Man","After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.","PG-13",2008,"https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg",new Date(),7.9,"Movie",new PricingCategory(2L,"New",5D,2,2D));

        when(movieRepository.findByIdIfHasStock(1L)).thenReturn(movie1);
        when(movieRepository.findByIdIfHasStock(2L)).thenReturn(movie2);

        ResponseEntity<Response<CartResponse>> responseEntity = cartController.checkoutMovies(checkoutRequests);
        CartResponse cartResponse = responseEntity.getBody().getPayload();
        int expectedCartSize = 2;
        Double expectedTotalCost = 19D;

        assertEquals(expectedCartSize, cartResponse.getCartItemList().size());
        assertEquals(expectedTotalCost, cartResponse.getTotalCost());
    }

    @Test
    public void givenListOfMoviesShouldReturnOutOfStockMovies() {

        List<CheckoutRequest> checkoutRequests = new ArrayList<>();
        checkoutRequests.add(new CheckoutRequest(1L,10));
        checkoutRequests.add(new CheckoutRequest(2L,5));

        Movie movie1 = new Movie(1L,"Iron Man","After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.","PG-13",2008,"https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg",new Date(),7.9,"Movie",new PricingCategory(1L,"Default",3D,5,1D));
        Movie movie2 = new Movie(2L,"Super Man","After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.","PG-13",2008,"https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg",new Date(),7.9,"Movie",new PricingCategory(2L,"New",5D,2,2D));

        when(movieRepository.findByIdIfHasStock(movie1.getMid())).thenReturn(movie1);
        when(movieRepository.findByIdIfHasStock(movie2.getMid())).thenReturn(null);

        ResponseEntity<Response<CartResponse>> responseEntity = cartController.checkoutMovies(checkoutRequests);
        CartResponse cartResponse = responseEntity.getBody().getPayload();

        assertEquals(1, cartResponse.getOutOfStockMoviesIds().size());
        assertEquals(movie2.getMid(), cartResponse.getOutOfStockMoviesIds().get(0));
    }

}
