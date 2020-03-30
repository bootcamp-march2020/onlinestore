package com.movie.onlinestore;

import com.movie.onlinestore.model.Director;
import com.movie.onlinestore.repository.DirectorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.Assert.assertEquals;

@AutoConfigureTestDatabase
@ImportAutoConfiguration
@DataJpaTest
public class DirectorRepositoryTest {

    @Autowired
    DirectorRepository directorRepository;

    @Test
    public void testFindOrCreateShouldReturnTheExistingDirectorWhenObjectExistsForTheName(){
        Director director = new Director(null,"Maniratnam");
        director = directorRepository.save(director);
        long countBeforeCall = directorRepository.count();
        Long existingDirectorId = director.getId();
        Director directorFound = directorRepository.findOrCreate("Maniratnam");
        long countAfterCall = directorRepository.count();
        assertEquals(countBeforeCall,countAfterCall);
        assertEquals(existingDirectorId,directorFound.getId());

    }

    @Test
    public void testFindOrCreateShouldCreateAnObjectWhenDirectorIsNotPresentAlready(){
        long countBeforeCall = directorRepository.count();
        directorRepository.findOrCreate("Sankar");
        long countAfterCall = directorRepository.count();
        assertEquals(countBeforeCall+1,countAfterCall);

    }
}
