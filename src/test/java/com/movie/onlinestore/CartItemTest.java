package com.movie.onlinestore;


import com.movie.onlinestore.model.CartItem;
import com.movie.onlinestore.model.Movie;
import com.movie.onlinestore.model.PricingCategory;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;


public class CartItemTest {

    @Test
    public void testCartItemCostCalculation() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Movie movie = new Movie(1l,"Iron Man","After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.","PG-13",2008,"https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg",sdf.parse("2008-05-02"),7.9,"Movie",new PricingCategory(1L,3D,5,1D));
        CartItem cartItem = new CartItem(movie,10);
        Double expectedCost = 8D;
        assertEquals(expectedCost,cartItem.getCost());
    }
}
