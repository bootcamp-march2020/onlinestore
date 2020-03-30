package com.movie.onlinestore;

import com.movie.onlinestore.model.Language;
import com.movie.onlinestore.repository.LanguageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.Assert.assertEquals;

@AutoConfigureTestDatabase
@ImportAutoConfiguration
@DataJpaTest
public class LanguageRepositoryTest {

    @Autowired
    LanguageRepository languageRepository;

    @Test
    public void testFindOrCreateShouldReturnTheExistingLanguageWhenObjectExistsForTheName(){
        Language language = new Language(null,"English");
        language = languageRepository.save(language);
        long countBeforeCall = languageRepository.count();
        Long existingLanguageId = language.getId();
        Language languageFound = languageRepository.findOrCreate("English");
        long countAfterCall = languageRepository.count();
        assertEquals(countBeforeCall,countAfterCall);
        assertEquals(existingLanguageId,languageFound.getId());

    }

    @Test
    public void testFindOrCreateShouldCreateAnObjectWhenLanguageIsNotPresentAlready(){
        long countBeforeCall = languageRepository.count();
        languageRepository.findOrCreate("Tamil");
        long countAfterCall = languageRepository.count();
        assertEquals(countBeforeCall+1,countAfterCall);

    }
}
