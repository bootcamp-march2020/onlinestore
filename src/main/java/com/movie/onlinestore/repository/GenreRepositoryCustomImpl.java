package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Optional;

public class GenreRepositoryCustomImpl implements GenreRepositoryCustom {

    @Autowired
    @Lazy
    GenreRepository genreRepository;

    @Override
    public Genre findOrCreate(String name) {
        Genre genre = null;
        Optional<Genre> genreRecord = genreRepository.findByName(name);
        if(genreRecord.isPresent()){
            genre = genreRecord.get();
        }
        else{
            genre = new Genre(null,name);
            genre = genreRepository.save(genre);
        }
        return genre;
    }
}
