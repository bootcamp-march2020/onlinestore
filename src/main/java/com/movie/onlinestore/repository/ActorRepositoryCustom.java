package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.Actor;


public interface ActorRepositoryCustom {

    public Actor findOrCreate(String name);

}
