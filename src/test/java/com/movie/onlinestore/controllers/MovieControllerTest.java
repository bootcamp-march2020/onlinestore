package com.movie.onlinestore.controllers;

import com.movie.onlinestore.controllers.MovieController;
import com.movie.onlinestore.model.Movie;
import com.movie.onlinestore.model.MovieInventory;
import com.movie.onlinestore.model.Response;
import com.movie.onlinestore.repository.MovieInventoryRepository;

import com.movie.onlinestore.service.MovieImportService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import static com.movie.onlinestore.repository.MovieInventorySpecifications.hasStock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class MovieControllerTest {
    @InjectMocks MovieController movieController;

    @Mock
    MovieInventoryRepository movieInventoryRepository;
    @Mock
    MovieImportService movieImportService;

    @Test
    public void testMovielistPositive() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Movie movie1 = new Movie(1l,"Iron Man","After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.","PG-13",2008,"https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg",sdf.parse("2008-05-02"),7.9,"Movie",null);

        List<MovieInventory> movieInventoryList = new ArrayList<>();
        movieInventoryList.add(new MovieInventory(1L,movie1,2,2));

        Movie movie2 = new Movie(2l,"Super Man","After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.","PG-13",2008,"https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg",sdf.parse("2008-05-02"),7.9,"Movie",null);
        movieInventoryList.add(new MovieInventory(2L,movie2,2,2));

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);

        when(movieInventoryRepository.findAll(hasStock())).thenReturn(movieInventoryList);

        ResponseEntity<Response<List<Movie>>> responseEntity = movieController.movielist();
        List<Movie> expectedMovie = responseEntity.getBody().getPayload();

        assertEquals(movieList.size(), expectedMovie.size());
        assertEquals(movieList.get(0), expectedMovie.get(0));
        assertEquals(movieList.get(1), expectedMovie.get(1));
    }

}
