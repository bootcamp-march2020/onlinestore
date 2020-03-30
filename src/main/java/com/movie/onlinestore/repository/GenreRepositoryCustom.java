package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.Genre;

public interface GenreRepositoryCustom {

    public Genre findOrCreate(String name);
}
