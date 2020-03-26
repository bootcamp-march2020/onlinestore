package com.movie.onlinestore;

import com.movie.onlinestore.controllers.MovieController;
import com.movie.onlinestore.model.Movie;
import com.movie.onlinestore.repository.MovieRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunListener;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class MovieControllerTest {
    @InjectMocks MovieController movieController;

    @Mock MovieRepository movieRepository;
    @Test
    public void testMovielistPositive() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1l,"Iron Man","After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.","PG-13",2008,"https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg",sdf.parse("2008-05-02"),7.9,"Movie",null));
        movies.add(new Movie(2l,"Super Man","After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.","PG-13",2008,"https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg",sdf.parse("2008-05-02"),7.9,"Movie",null));

        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> expectedMovie = movieController.movielist();

        assertEquals(movies.size(), expectedMovie.size());
        assertEquals(movies.get(0), expectedMovie.get(0));
        assertEquals(movies.get(1), expectedMovie.get(1));
    }

}
