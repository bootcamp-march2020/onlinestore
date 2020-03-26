package com.movie.onlinestore.controllers;

import com.movie.onlinestore.model.Movie;
import com.movie.onlinestore.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller

public class MovieController {
    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/movies")
    @ResponseBody
    public List<Movie> movielist() {
        return movieRepository.findAll();
    }

}
