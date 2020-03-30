package com.movie.onlinestore.service;

import com.movie.onlinestore.model.*;
import com.movie.onlinestore.repository.*;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    ImportedFileRepository importedFileRepository;
    @Autowired
    PricingCategoryRepository pricingCategoryRepository;
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    DirectorRepository directorRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    MovieInventoryRepository movieInventoryRepository;

    @Value( "${importdata.prefix}" )
    private String urlPrefix;

    public String nextFileName(){
        Long importCount = importedFileRepository.count();
        return String.format("V%d_data.csv",importCount+1);
    }

    @Transactional
    public String importFile(String fileName){
        List<MovieInventory> movieInventoryList = new ArrayList<>();
        List<String> errorList = new ArrayList<String>();
        String message = "";
        try {
            URL url = new URL(urlPrefix + fileName);
            CSVReader csvReader = new CSVReader(new InputStreamReader(url.openStream()));
            String[] record;
            Integer lineCounter = 0;
            if (csvReader.readNext() != null) {
                while ((record = csvReader.readNext()) != null) {
                    lineCounter++;
                     Movie movie = constructMovie(record, lineCounter, errorList);
                    if (movie != null) {
                        Integer stockCount = Integer.parseInt(record[13]);
                        movie = movieRepository.save(movie);
                        MovieInventory movieInventory = new MovieInventory(movie, stockCount);
                        movieInventory = movieInventoryRepository.save(movieInventory);
                        movieInventoryList.add(movieInventory);
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(lineCounter);
            sb.append(" records are in the file. ");
            sb.append(movieInventoryList.size());
            sb.append(" records inserted successfully. ");
            sb.append(errorList.size());
            sb.append(" records has errors. ");
            sb.append(String.join(" ",errorList));
            message = sb.toString();
            ImportedFile importedFile = new ImportedFile(fileName,message);
            importedFileRepository.save(importedFile);
        }
        catch (IOException ioe){
            message = "Got error while import. Kindly check the file  is there with the correct naming convention.";
        }
        return message;
    }

    public Movie constructMovie(String[] record, Integer lineCounter, List<String> errorList) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Movie movie = null;
        if(record.length == 14){
            Optional<PricingCategory>  pricingCategoryRecord = pricingCategoryRepository.findByName(record[8]);
            if(pricingCategoryRecord.isPresent()){
                try {
                    String title = record[0];
                    String description = record[1];
                    String rated = record[2];
                    Integer year = Integer.parseInt(record[3]);
                    String posterUrlString = record[4];
                    Date releaseDate = sdf.parse(record[5]);
                    Double ratings = Double.parseDouble(record[6]);
                    String type = record[7];
                    movie = new Movie(null, title, description, rated, year, posterUrlString, releaseDate, ratings, type, pricingCategoryRecord.get());
                    addActorsToMovie(movie, record[9]);
                    addDirectorsToMovie(movie, record[10]);
                    addLanguagesToMovie(movie, record[11]);
                    addGenresToMovie(movie, record[12]);
                }
                catch (ParseException pe){
                    StringBuilder sb = new StringBuilder();
                    sb.append("Line ");
                    sb.append(lineCounter);
                    sb.append(" release date is in wrong format");
                    errorList.add(sb.toString());
                }
            }
            else{
                StringBuilder sb = new StringBuilder();
                sb.append("Line ");
                sb.append(lineCounter);
                sb.append(" has invalid pricing type");
                errorList.add(sb.toString());
            }
        }
        else{
            StringBuilder sb = new StringBuilder();
            sb.append("Line ");
            sb.append(lineCounter);
            sb.append(" has data mismatch");
            errorList.add(sb.toString());
        }
        return movie;
    }

    private void addActorsToMovie(Movie movie, String actorStr){
        String[] actors = actorStr.split(",");
        for(String actor : actors){
            String actorName = actor.trim();
            if(!actorName.equals("")){
                movie.addToActorSet(findOrCreateActor(actorName));
            }
        }
    }

    private void addDirectorsToMovie(Movie movie, String directorStr){
        String[] directors = directorStr.split(",");
        for(String director : directors){
            String directorName = director.trim();
            if(!directorName.equals("")){
                movie.addToDirectorSet(findOrCreateDirector(directorName));
            }
        }
    }

    private void addLanguagesToMovie(Movie movie, String languageStr){
        String[] languages = languageStr.split(",");
        for(String language : languages){
            String languageName = language.trim();
            if(!languageName.equals("")){
                movie.addToLanguageSet(findOrCreateLanguage(languageName));
            }
        }
    }

    private void addGenresToMovie(Movie movie, String genreStr){
        String[] genres = genreStr.split(",");
        for(String genre : genres){
            String genreName = genre.trim();
            if(!genreName.equals("")){
                movie.addToGenreSet(findOrCreateGenre(genreName));
            }
        }
    }

    private Actor findOrCreateActor(String name){
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

    private Director findOrCreateDirector(String name){
        Director director = null;
        Optional<Director> directorRecord = directorRepository.findByName(name);
        if(directorRecord.isPresent()){
            director = directorRecord.get();
        }
        else{
            director = new Director(null,name);
            director = directorRepository.save(director);
        }
        return director;
    }

    private Language findOrCreateLanguage(String name){
        Language language = null;
        Optional<Language> languageRecord = languageRepository.findByName(name);
        if(languageRecord.isPresent()){
            language = languageRecord.get();
        }
        else{
            language = new Language(null,name);
            language = languageRepository.save(language);
        }
        return language;
    }

    private Genre findOrCreateGenre(String name){
        Genre genre = null;
        Optional<Genre> genreRecord = genreRepository.findByName(name);
        if(genreRecord.isPresent()){
            genre = genreRecord.get();
        }
        else{
            genre = new Genre(null,name);
            genre = genreRepository.save(genre);
        }
        return genre;
    }

    public void setImportedFileRepository(ImportedFileRepository importedFileRepository) {
        this.importedFileRepository = importedFileRepository;
    }

    public void setPricingCategoryRepository(PricingCategoryRepository pricingCategoryRepository) {
        this.pricingCategoryRepository = pricingCategoryRepository;
    }

    public void setActorRepository(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public void setDirectorRepository(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public void setLanguageRepository(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public void setMovieRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void setMovieInventoryRepository(MovieInventoryRepository movieInventoryRepository) {
        this.movieInventoryRepository = movieInventoryRepository;
    }
}
