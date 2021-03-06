package com.movie.onlinestore.controllers;

import com.movie.onlinestore.UrlConstants;
import com.movie.onlinestore.model.Movie;
import com.movie.onlinestore.model.MovieInventory;
import com.movie.onlinestore.model.Response;
import com.movie.onlinestore.repository.MovieInventoryRepository;
import com.movie.onlinestore.service.MovieImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.movie.onlinestore.repository.MovieInventorySpecifications.hasStock;


@Controller
public class MovieController {
    @Autowired
    MovieInventoryRepository movieInventoryRepository;
    @Autowired
    MovieImportService movieImportService;

    @GetMapping(UrlConstants.URL_PATH_MOVIE_LIST)
    @ResponseBody
    public ResponseEntity<Response<List<Movie>>> movielist() {
        List<MovieInventory> movieInventories = movieInventoryRepository.findAll(hasStock());
        List<Movie> movies = movieInventories.stream()
                .map(e->e.getMovie())
                .collect(Collectors.toList());
        return new ResponseEntity<>(Response.success(movies), HttpStatus.OK);
    }

    @GetMapping(UrlConstants.URL_PATH_IMPORT_DATA)
    @ResponseBody
    public ResponseEntity<Response<String>> importData() throws IOException {
        String nextFilename =  movieImportService.nextFileName();
        String status = movieImportService.importFile(nextFilename);
        return new ResponseEntity<>(Response.success(status), HttpStatus.ACCEPTED);
    }
}
