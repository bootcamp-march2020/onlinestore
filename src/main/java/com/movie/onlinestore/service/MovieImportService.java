package com.movie.onlinestore.service;

import com.movie.onlinestore.ImportFileReder;
import com.movie.onlinestore.model.*;
import com.movie.onlinestore.repository.*;
import com.opencsv.CSVReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
public class MovieImportService {

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
    @Autowired
    ImportFileReder importFileReder;

    @Value( "${importdata.prefix}" )
    private String urlPrefix;
    @Value("${omdbapi.apiUrl}")
    private String apiUrl;
    @Value("${omdbapi.apiKey}")
    private String apiKey;

    public String nextFileName(){
        Long importCount = importedFileRepository.count();
        return String.format("V%d_data.csv",importCount+1);
    }

    @Transactional
    public String importFile(String fileName){
        Integer successCount = 0;
        List<String> errorList = new ArrayList<String>();
        String message = "";
        try {
            List<String[]> recordList = importFileReder.obtainRecords(fileName);
            Integer lineCount = 0;
            for(String[] record : recordList){
                lineCount++;
                if(saveMovieInventory(record,lineCount,errorList)){
                    successCount++;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(lineCount).append(" records are in the file. ").append(successCount).append(" records inserted/updated successfully. ")
                    .append(errorList.size()).append(" records has errors. ").append(String.join(" ",errorList));
            message = sb.toString();
            ImportedFile importedFile = new ImportedFile(fileName,message);
            importedFileRepository.save(importedFile);
        }
        catch (IOException ioe){
            message = "Got error while import. Kindly check the file  is there with the correct naming convention.";
        }
        return message;
    }

    public Boolean saveMovieInventory(String[] record, Integer lineCounter, List<String> errorList){
        if(record.length == 3) {
            Optional<MovieInventory> movieInventoryRecord =  movieInventoryRepository.findByImdbId(record[0]);
            if(movieInventoryRecord.isPresent()){
                MovieInventory  movieInventory = movieInventoryRecord.get();
                Integer stockCount = Integer.parseInt(record[2]);
                movieInventory.addStock(stockCount);
                movieInventoryRepository.save(movieInventory);
                return true;
            }
            else{
                Movie movie = constructMovie(record, lineCounter, errorList);
                if (movie != null) {
                    Integer stockCount = Integer.parseInt(record[2]);
                    movie = movieRepository.save(movie);
                    MovieInventory movieInventory = new MovieInventory(movie, stockCount);
                    movieInventoryRepository.save(movieInventory);
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        else{
            StringBuilder sb = new StringBuilder();
            sb.append("Line ").append(lineCounter).append(" has data mismatch");
            errorList.add(sb.toString());
            return false;
        }
    }

    public Movie constructMovie(String[] record, Integer lineCounter, List<String> errorList) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Movie movie = null;
        Optional<PricingCategory>  pricingCategoryRecord = pricingCategoryRepository.findByName(record[1]);
        if(pricingCategoryRecord.isPresent()){
            try {
                movie = constructMovieFromApi(record[0],pricingCategoryRecord.get());
            }
            catch (IOException | NullPointerException e){
                StringBuilder sb = new StringBuilder();
                sb.append("Line ");
                sb.append(lineCounter);
                sb.append(" Unable to get Movie Details From API");
                errorList.add(sb.toString());
            }
        }
        else{
            StringBuilder sb = new StringBuilder();
            sb.append("Line ").append(lineCounter).append(" has invalid pricing type");
            errorList.add(sb.toString());
        }
        return movie;
    }

    public Movie constructMovieFromApi(String movieId, PricingCategory pricingCategory) throws IOException  {
        Movie movie = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            URL url = new URL(apiUrl+"?apikey="+apiKey+"&i="+movieId);
            Object obj = new JSONParser().parse(new InputStreamReader(url.openStream()));
            JSONObject jo = (JSONObject) obj;
            String imdbId = jo.get("imdbID").toString();
            String title = jo.get("Title").toString();
            String description = jo.get("Plot").toString();
            String rated = jo.get("Rated").toString();
            Integer year = Integer.parseInt(jo.get("Year").toString());
            String posterUrlString = jo.get("Poster").toString();
            Date releaseDate = sdf.parse(jo.get("Released").toString());
            Double ratings = Double.parseDouble(jo.get("imdbRating").toString());
            String type = "Movie";
            movie = new Movie(null,imdbId, title, description, rated, year, posterUrlString, releaseDate, ratings, type, pricingCategory);
            addActorsToMovie(movie, jo.get("Actors").toString());
            addDirectorsToMovie(movie, jo.get("Director").toString());
            addLanguagesToMovie(movie, jo.get("Language").toString());
            addGenresToMovie(movie, jo.get("Genre").toString());
        }
        catch (org.json.simple.parser.ParseException | ParseException e){

        }
        return movie;
    }


    private void addActorsToMovie(Movie movie, String actorStr){
        String[] actors = actorStr.split(",");
        for(String actor : actors){
            String actorName = actor.trim();
            if(!actorName.equals("")){
                movie.addToActorSet(actorRepository.findOrCreate(actorName));
            }
        }
    }

    private void addDirectorsToMovie(Movie movie, String directorStr){
        String[] directors = directorStr.split(",");
        for(String director : directors){
            String directorName = director.trim();
            if(!directorName.equals("")){
                movie.addToDirectorSet(directorRepository.findOrCreate(directorName));
            }
        }
    }

    private void addLanguagesToMovie(Movie movie, String languageStr){
        String[] languages = languageStr.split(",");
        for(String language : languages){
            String languageName = language.trim();
            if(!languageName.equals("")){
                movie.addToLanguageSet(languageRepository.findOrCreate(languageName));
            }
        }
    }

    private void addGenresToMovie(Movie movie, String genreStr){
        String[] genres = genreStr.split(",");
        for(String genre : genres){
            String genreName = genre.trim();
            if(!genreName.equals("")){
                movie.addToGenreSet(genreRepository.findOrCreate(genreName));
            }
        }
    }

    public void setApiDetails(String apiUrl, String apiKey) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
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

    public void setImportFileReder(ImportFileReder importFileReder) {
        this.importFileReder = importFileReder;
    }
}
