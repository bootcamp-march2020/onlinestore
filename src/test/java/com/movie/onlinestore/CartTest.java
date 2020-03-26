package com.movie.onlinestore;

import com.movie.onlinestore.model.Cart;
import com.movie.onlinestore.model.Movie;
import com.movie.onlinestore.model.PricingCategory;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class CartTest {

    @Test
    public void testAddMovieToCartShould() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Cart cart = new Cart();
        Movie movie1 = new Movie(1l,"Iron Man","After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.","PG-13",2008,"https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg",sdf.parse("2008-05-02"),7.9,"Movie",new PricingCategory(1L,3D,5,1D));
        Movie movie2 = new Movie(2l,"Iron Man 2","After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.","PG-13",2008,"https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg",sdf.parse("2008-05-02"),7.9,"Movie",new PricingCategory(2L,5D,7,2D));
        cart.addMovieToCart(movie1,10);
        cart.addMovieToCart(movie2,8);
        Double expectedTotalCost = 15D;
        assertEquals(expectedTotalCost,cart.getTotalCost());
    }

}