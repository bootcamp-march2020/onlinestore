package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.Actor;
import com.movie.onlinestore.repository.ActorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.Assert.assertEquals;

@AutoConfigureTestDatabase
@ImportAutoConfiguration
@DataJpaTest
public class ActorRepositoryTest {

    @Autowired
    ActorRepository actorRepository;

    @Test
    public void testFindOrCreateShouldReturnTheExistingActorWhenObjectExistsForTheName(){
        Actor actor = new Actor(null,"Rajini");
        actor = actorRepository.save(actor);
        long countBeforeCall = actorRepository.count();
        Long existingActorId = actor.getId();
        Actor actorFound = actorRepository.findOrCreate("Rajini");
        long countAfterCall = actorRepository.count();
        assertEquals(countBeforeCall,countAfterCall);
        assertEquals(existingActorId,actorFound.getId());

    }

    @Test
    public void testFindOrCreateShouldCreateAnObjectWhenActorIsNotPresentAlready(){
        long countBeforeCall = actorRepository.count();
        actorRepository.findOrCreate("Kamal");
        long countAfterCall = actorRepository.count();
        assertEquals(countBeforeCall+1,countAfterCall);

    }

}
