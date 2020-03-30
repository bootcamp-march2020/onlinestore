package com.movie.onlinestore;

import com.movie.onlinestore.model.*;
import com.movie.onlinestore.repository.*;
import com.movie.onlinestore.service.MovieImportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class MovieImportServiceTest {


    MovieImportService movieImportService;
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
        movieImportService = new MovieImportService();
        importedFileRepository = mock(ImportedFileRepository.class);
        movieImportService.setImportedFileRepository(importedFileRepository);
        pricingCategoryRepository = mock(PricingCategoryRepository.class);
        movieImportService.setPricingCategoryRepository(pricingCategoryRepository);
        actorRepository = mock(ActorRepository.class);
        movieImportService.setActorRepository(actorRepository);
        directorRepository = mock(DirectorRepository.class);
        movieImportService.setDirectorRepository(directorRepository);
        genreRepository = mock(GenreRepository.class);
        movieImportService.setGenreRepository(genreRepository);
        languageRepository = mock(LanguageRepository.class);
        movieImportService.setLanguageRepository(languageRepository);
        movieRepository = mock(MovieRepository.class);
        movieImportService.setMovieRepository(movieRepository);
        movieInventoryRepository = mock(MovieInventoryRepository.class);
        movieImportService.setMovieInventoryRepository(movieInventoryRepository);
        movieImportService.setApiDetails("http://www.omdbapi.com/","343f7128");
    }

    @Test
    public void testNextFileNameShouldReturnStringWithImportedFileCountPlusOne(){
        when(importedFileRepository.count()).thenReturn(5L);
        String expectedFilename = "V6_data.csv";
        assertEquals(expectedFilename, movieImportService.nextFileName());
    }

    @Test
    public void testNextFileNameShouldReturnV1WhenImportedFileCountIsZero(){
        importedFileRepository = mock(ImportedFileRepository.class);
        when(importedFileRepository.count()).thenReturn(0L);
        String expectedFilename = "V1_data.csv";
        assertEquals(expectedFilename, movieImportService.nextFileName());
    }


    @Test
    public void testConstructMovieInventoryWithInvalidRecord() throws ParseException {
        String[] record = {"Poltergeist","A family's home is haunted by a host of demonic ghosts."};
        List<String> errorList = new ArrayList<>();
        Movie movie = movieImportService.constructMovie(record,1,errorList);
        assertNull(movie);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 has data mismatch",errorList.get(0));
    }

    @Test
    public void testConstructMovieInventoryWithInvalidPriceCategoryName() throws ParseException {
        String[] record = {"tt0084516","Clasic","6"};
        List<String> errorList = new ArrayList<>();
        when(pricingCategoryRepository.findByName("Clasic")).thenReturn(Optional.empty());
        Movie movie = movieImportService.constructMovie(record,1,errorList);
        assertNull(movie);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 has invalid pricing type",errorList.get(0));
    }


    @Test
    public void testConstructMovieInventoryWithValidRecord() throws ParseException {
        String[] record = {"tt0084516","Default","6"};
        List<String> errorList = new ArrayList<>();
        PricingCategory pricingCategory = new PricingCategory(1L,"Default",3.25D,5,1D);
        when(pricingCategoryRepository.findByName("Default")).thenReturn(Optional.of(pricingCategory));
        Actor actor1 = new Actor(null,"Craig T. Nelson");
        Actor actor2 = new Actor(null,"JoBeth Williams");
        Actor actor3 = new Actor(null,"Beatrice Straight");
        Actor actor4 = new Actor(null,"Dominique Dunne");
        Director director = new Director(null,"Tobe Hooper");
        Language language = new Language(null,"English");
        Genre genre1 = new Genre(null,"Horror");
        Genre genre2 = new Genre(null,"Thriller");
        when(actorRepository.findOrCreate("Craig T. Nelson")).thenReturn(actor1);
        when(actorRepository.findOrCreate("JoBeth Williams")).thenReturn(actor2);
        when(actorRepository.findOrCreate("Beatrice Straight")).thenReturn(actor3);
        when(actorRepository.findOrCreate("Dominique Dunne")).thenReturn(actor4);
        when(directorRepository.findOrCreate("Tobe Hooper")).thenReturn(director);
        when(languageRepository.findOrCreate("English")).thenReturn(language);
        when(genreRepository.findOrCreate("Horror")).thenReturn(genre1);
        when(genreRepository.findOrCreate("Thriller")).thenReturn(genre2);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Movie expectedMovie  = new Movie(null,"Poltergeist","A family's home is haunted by a host of demonic ghosts.","PG",1982,"https://m.media-amazon.com/images/M/MV5BNzliZmRlYTctYmNkYS00NzE5LWI1OWQtMTRiODY5MDMwMTVkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg",sdf.parse("04 Jun 1982"),7.3D,"Movie",pricingCategory);
        expectedMovie.addToActorSet(actor1);
        expectedMovie.addToActorSet(actor2);
        expectedMovie.addToActorSet(actor3);
        expectedMovie.addToActorSet(actor4);
        expectedMovie.addToDirectorSet(director);
        expectedMovie.addToLanguageSet(language);
        expectedMovie.addToGenreSet(genre1);
        expectedMovie.addToGenreSet(genre2);
        Movie obtainedMovie = movieImportService.constructMovie(record,1,errorList);
        int expectedErrorSize = 0;
        assertEquals(expectedErrorSize,errorList.size());
        assertEquals(expectedMovie,obtainedMovie);
    }

    @Test
    public void testConstructMovieFromApi() throws IOException, ParseException {
        String movieId = "tt0084516";
        PricingCategory pricingCategory = new PricingCategory(1L,"Default",3.25D,5,1D);
        Actor actor1 = new Actor(null,"Craig T. Nelson");
        Actor actor2 = new Actor(null,"JoBeth Williams");
        Actor actor3 = new Actor(null,"Beatrice Straight");
        Actor actor4 = new Actor(null,"Dominique Dunne");
        Director director = new Director(null,"Tobe Hooper");
        Language language = new Language(null,"English");
        Genre genre1 = new Genre(null,"Horror");
        Genre genre2 = new Genre(null,"Thriller");
        when(actorRepository.findOrCreate("Craig T. Nelson")).thenReturn(actor1);
        when(actorRepository.findOrCreate("JoBeth Williams")).thenReturn(actor2);
        when(actorRepository.findOrCreate("Beatrice Straight")).thenReturn(actor3);
        when(actorRepository.findOrCreate("Dominique Dunne")).thenReturn(actor4);
        when(directorRepository.findOrCreate("Tobe Hooper")).thenReturn(director);
        when(languageRepository.findOrCreate("English")).thenReturn(language);
        when(genreRepository.findOrCreate("Horror")).thenReturn(genre1);
        when(genreRepository.findOrCreate("Thriller")).thenReturn(genre2);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Movie expectedMovie  = new Movie(null,"Poltergeist","A family's home is haunted by a host of demonic ghosts.","PG",1982,"https://m.media-amazon.com/images/M/MV5BNzliZmRlYTctYmNkYS00NzE5LWI1OWQtMTRiODY5MDMwMTVkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg",sdf.parse("04 Jun 1982"),7.3D,"Movie",pricingCategory);
        expectedMovie.addToActorSet(actor1);
        expectedMovie.addToActorSet(actor2);
        expectedMovie.addToActorSet(actor3);
        expectedMovie.addToActorSet(actor4);
        expectedMovie.addToDirectorSet(director);
        expectedMovie.addToLanguageSet(language);
        expectedMovie.addToGenreSet(genre1);
        expectedMovie.addToGenreSet(genre2);
        Movie obtainedMovie = movieImportService.constructMovieFromApi(movieId,pricingCategory);
        assertEquals(expectedMovie,obtainedMovie);
    }

}
