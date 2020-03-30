package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.Genre;
import com.movie.onlinestore.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.Assert.assertEquals;

@AutoConfigureTestDatabase
@ImportAutoConfiguration
@DataJpaTest
public class GenreRepositoryTest {

    @Autowired
    GenreRepository genreRepository;

    @Test
    public void testFindOrCreateShouldReturnTheExistingGenreWhenObjectExistsForTheName(){
        Genre genre = new Genre(null,"Horor");
        genre = genreRepository.save(genre);
        long countBeforeCall = genreRepository.count();
        Long existingGenreId = genre.getId();
        Genre genreFound = genreRepository.findOrCreate("Horor");
        long countAfterCall = genreRepository.count();
        assertEquals(countBeforeCall,countAfterCall);
        assertEquals(existingGenreId,genreFound.getId());

    }

    @Test
    public void testFindOrCreateShouldCreateAnObjectWhenGenreIsNotPresentAlready(){
        long countBeforeCall = genreRepository.count();
        genreRepository.findOrCreate("Comedy");
        long countAfterCall = genreRepository.count();
        assertEquals(countBeforeCall+1,countAfterCall);

    }
}
