package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.Director;

public interface DirectorRepositoryCustom {

    public Director findOrCreate(String name);
}
