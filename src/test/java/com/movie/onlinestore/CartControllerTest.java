package com.movie.onlinestore;


import com.movie.onlinestore.controllers.CartController;
import com.movie.onlinestore.model.Cart;
import com.movie.onlinestore.model.Movie;
import com.movie.onlinestore.model.PricingCategory;
import com.movie.onlinestore.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void checkoutMoviesTest() {
        HashMap[] payload = new HashMap[2];
        HashMap<String, Object> item1 = new HashMap<>();
        item1.put("movieId", 1);
        item1.put("numberOfDays", 10);
        payload[0] = item1;
        HashMap<String, Object> item2 = new HashMap<>();
        item2.put("movieId", 2);
        item2.put("numberOfDays", 5);
        payload[1] = item2;

        Movie movie1 = new Movie(1l,"Iron Man","After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.","PG-13",2008,"https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg",new Date(),7.9,"Movie",new PricingCategory(1L,3D,5,1D));
        Movie movie2 = new Movie(2l,"Super Man","After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.","PG-13",2008,"https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg",new Date(),7.9,"Movie",new PricingCategory(2L,5D,2,2D));

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));
        when(movieRepository.findById(2L)).thenReturn(Optional.of(movie2));

        Cart cart = cartController.checkoutMovies(payload);

        int expectedCartSize = 2;
        Double expectedTotalCost = 19D;

        assertEquals(expectedCartSize, cart.getCartItemList().size());
        assertEquals(expectedTotalCost, cart.getTotalCost());
    }

}
