package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Optional;

public class DirectorRepositoryCustomImpl implements DirectorRepositoryCustom {

    @Autowired
    @Lazy
    DirectorRepository directorRepository;

    @Override
    public Director findOrCreate(String name) {
        Director director = null;
        Optional<Director> directorRecord = directorRepository.findByName(name);
        if(directorRecord.isPresent()){
            director = directorRecord.get();
        }
        else{
            director = new Director(null,name);
            director = directorRepository.save(director);
        }
        return director;
    }
}
