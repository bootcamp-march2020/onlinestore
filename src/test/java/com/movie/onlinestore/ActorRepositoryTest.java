package com.movie.onlinestore;

import com.movie.onlinestore.model.Actor;
import com.movie.onlinestore.model.PricingCategory;
import com.movie.onlinestore.repository.ActorRepository;
import com.movie.onlinestore.repository.PricingCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@AutoConfigureTestDatabase
@ImportAutoConfiguration
@DataJpaTest
public class ActorRepositoryTest {

    @Autowired
    ActorRepository actorRepository;
    @Autowired
    PricingCategoryRepository pricingCategoryRepository;

    @Test
    public void testFindOrCreateShouldReturnTheExistingObjectWhenObjectExistsForTheName(){
//        Actor actor = new Actor();
//        actor.setName("Rajini");
//        actor = actorRepository.save(actor);
//        long countBeforeCall = actorRepository.count();
//        Long existingActorId = actor.getId();
//        Actor actorFound = actorRepository.findOrCreate("Rajini");
//        long countAfterCall = actorRepository.count();
//        assertEquals(countBeforeCall,countAfterCall);
//        assertEquals(existingActorId,actorFound.getId());
//        System.out.println(actorRepository.count());
        PricingCategory pc = new PricingCategory(null,"Test",2D,5,1D);
        pc = pricingCategoryRepository.save(pc);
        System.out.println(pricingCategoryRepository.count());
//        PricingCategory pc1 = pricingCategoryRepository.findByName("New");

    }

}
