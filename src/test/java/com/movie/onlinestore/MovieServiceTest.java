package com.movie.onlinestore;

import com.movie.onlinestore.model.*;
import com.movie.onlinestore.repository.*;
import com.movie.onlinestore.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class MovieServiceTest {

    MovieService movieService;
    ImportedFileRepository importedFileRepository;
    PricingCategoryRepository pricingCategoryRepository;
    ActorRepository actorRepository;
    DirectorRepository directorRepository;
    GenreRepository genreRepository;
    LanguageRepository languageRepository;
    MovieRepository movieRepository;
    MovieInventoryRepository movieInventoryRepository;

    @BeforeEach
    public void setUp(){
        movieService = new MovieService();
        importedFileRepository = mock(ImportedFileRepository.class);
        movieService.setImportedFileRepository(importedFileRepository);
        pricingCategoryRepository = mock(PricingCategoryRepository.class);
        movieService.setPricingCategoryRepository(pricingCategoryRepository);
        actorRepository = mock(ActorRepository.class);
        movieService.setActorRepository(actorRepository);
        directorRepository = mock(DirectorRepository.class);
        movieService.setDirectorRepository(directorRepository);
        genreRepository = mock(GenreRepository.class);
        movieService.setGenreRepository(genreRepository);
        languageRepository = mock(LanguageRepository.class);
        movieService.setLanguageRepository(languageRepository);
        movieRepository = mock(MovieRepository.class);
        movieService.setMovieRepository(movieRepository);
        movieInventoryRepository = mock(MovieInventoryRepository.class);
        movieService.setMovieInventoryRepository(movieInventoryRepository);
    }

    @Test
    public void testNextFileNameShouldReturnStringWithImportedFileCountPlusOne(){
        when(importedFileRepository.count()).thenReturn(5L);
        String expectedFilename = "V6_data.csv";
        assertEquals(expectedFilename,movieService.nextFileName());
    }

    @Test
    public void testNextFileNameShouldReturnV1WhenImportedFileCountIsZero(){
        importedFileRepository = mock(ImportedFileRepository.class);
        when(importedFileRepository.count()).thenReturn(0L);
        String expectedFilename = "V1_data.csv";
        assertEquals(expectedFilename,movieService.nextFileName());
    }


    @Test
    public void testConstructMovieInventoryWithInvalidRecord() throws ParseException {
        String[] record = {"Poltergeist","A family's home is haunted by a host of demonic ghosts."};
        List<String> errorList = new ArrayList<>();
        Movie movie = movieService.constructMovie(record,1,errorList);
        assertNull(movie);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 has data mismatch",errorList.get(0));
    }

    @Test
    public void testConstructMovieInventoryWithInvalidPriceCategoryName() throws ParseException {
        String[] record = {"Poltergeist","A family's home is haunted by a host of demonic ghosts.","PG","1982","https://m.media-amazon.com/images/M/MV5BNzliZmRlYTctYmNkYS00NzE5LWI1OWQtMTRiODY5MDMwMTVkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg","04 Jun 1982","7.3","Movie","Default","Craig T. Nelson, JoBeth Williams","Tobe Hooper","English","Horror, Thriller","4"};
        List<String> errorList = new ArrayList<>();
        when(pricingCategoryRepository.findByName("Default")).thenReturn(Optional.empty());
        Movie movie = movieService.constructMovie(record,1,errorList);
        assertNull(movie);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 has invalid pricing type",errorList.get(0));
    }

    @Test
    public void testConstructMovieInventoryWithInvalidReleaseDateFormat() throws ParseException {
        String[] record = {"Poltergeist","A family's home is haunted by a host of demonic ghosts.","PG","1982","https://m.media-amazon.com/images/M/MV5BNzliZmRlYTctYmNkYS00NzE5LWI1OWQtMTRiODY5MDMwMTVkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg","04-01-1982","7.3","Movie","Default","Craig T. Nelson, JoBeth Williams","Tobe Hooper","English","Horror, Thriller","4"};
        List<String> errorList = new ArrayList<>();
        PricingCategory pricingCategory = new PricingCategory(1L,"Default",3.25D,5,1D);
        when(pricingCategoryRepository.findByName("Default")).thenReturn(Optional.of(pricingCategory));
        Movie movie = movieService.constructMovie(record,1,errorList);
        assertNull(movie);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 release date is in wrong format",errorList.get(0));
    }

    @Test
    public void testConstructMovieInventoryWithValidRecord() throws ParseException {
        String[] record = {"Poltergeist","A family's home is haunted by a host of demonic ghosts.","PG","1982","https://m.media-amazon.com/images/M/MV5BNzliZmRlYTctYmNkYS00NzE5LWI1OWQtMTRiODY5MDMwMTVkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg","04 Jun 1982","7.3","Movie","Default","Craig T. Nelson, JoBeth Williams","Tobe Hooper","English","Horror, Thriller","4"};
        List<String> errorList = new ArrayList<>();
        PricingCategory pricingCategory = new PricingCategory(1L,"Default",3.25D,5,1D);
        when(pricingCategoryRepository.findByName("Default")).thenReturn(Optional.of(pricingCategory));
        Actor actor1 = new Actor(null,"Craig T. Nelson");
        Actor actor2 = new Actor(null,"JoBeth Williams");
        Director director = new Director(null,"Tobe Hooper");
        Language language = new Language(null,"English");
        Genre genre1 = new Genre(null,"Horror");
        Genre genre2 = new Genre(null,"Thriller");
        when(actorRepository.findByName("Craig T. Nelson")).thenReturn(Optional.of(actor1));
        when(actorRepository.findByName("JoBeth Williams")).thenReturn(Optional.of(actor2));
        when(directorRepository.findByName("Tobe Hooper")).thenReturn(Optional.of(director));
        when(languageRepository.findByName("English")).thenReturn(Optional.of(language));
        when(genreRepository.findByName("Horror")).thenReturn(Optional.of(genre1));
        when(genreRepository.findByName("Thriller")).thenReturn(Optional.of(genre2));
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Movie expectedMovie  = new Movie(null,"Poltergeist","A family's home is haunted by a host of demonic ghosts.","PG",1982,"https://m.media-amazon.com/images/M/MV5BNzliZmRlYTctYmNkYS00NzE5LWI1OWQtMTRiODY5MDMwMTVkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg",sdf.parse("04 Jun 1982"),7.3D,"Movie",pricingCategory);
        when(movieRepository.save(any(Movie.class))).thenReturn(expectedMovie);
        when(movieInventoryRepository.save(any(MovieInventory.class))).thenReturn(new MovieInventory(expectedMovie,4));
        when(importedFileRepository.save(any(ImportedFile.class))).thenReturn(null);
        Movie obtainedMovie = movieService.constructMovie(record,1,errorList);
        int expectedErrorSize = 0;
        Integer expectedStockCount = 4;expectedMovie.addToActorSet(actor1);
        expectedMovie.addToActorSet(actor2);
        expectedMovie.addToDirectorSet(director);
        expectedMovie.addToLanguageSet(language);
        expectedMovie.addToGenreSet(genre1);
        expectedMovie.addToGenreSet(genre2);
        assertEquals(expectedErrorSize,errorList.size());
        assertEquals(expectedMovie,obtainedMovie);
    }

}
