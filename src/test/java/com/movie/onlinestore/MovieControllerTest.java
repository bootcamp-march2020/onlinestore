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
    public void testMovielistPositive() {

        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1l, "Iron Man"));
        movies.add(new Movie(2l, "Spider Man"));
        movies.add(new Movie(3l, "Super Man"));

        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> expectedMovie = movieController.movielist();

        assertEquals(movies.size(), expectedMovie.size());
        assertEquals(movies.get(0), expectedMovie.get(0));
        assertEquals(movies.get(1), expectedMovie.get(1));
        assertEquals(movies.get(2), expectedMovie.get(2));
    }

}
