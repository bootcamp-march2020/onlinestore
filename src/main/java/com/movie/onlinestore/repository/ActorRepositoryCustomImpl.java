package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Optional;

public class ActorRepositoryCustomImpl implements ActorRepositoryCustom {

    @Autowired
    @Lazy
    ActorRepository actorRepository;

    @Override
    public Actor findOrCreate(String name) {
        Actor actor = null;
        Optional<Actor> actorRecord = actorRepository.findByName(name);
        if(actorRecord.isPresent()){
            actor = actorRecord.get();
        }
        else{
            actor = new Actor(null,name);
            actor = actorRepository.save(actor);
        }
        return actor;
    }
}
