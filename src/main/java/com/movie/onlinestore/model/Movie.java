package com.movie.onlinestore.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long mid;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "rated")
    private  String rated;

    @Column(name = "year")
    private Integer year;

    @Column(name = "poster_url")
    private String  posterUrlString;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "ratings")
    private Double ratings;

    @Column(name = "type")
    private String type;

    @OneToOne
    @JoinColumn(name = "pricing_category_id")
    private PricingCategory pricingCategory;

    @ManyToMany
    @JoinTable(
            name = "movie_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    Set<Actor> actorSet;

    @ManyToMany
    @JoinTable(
            name = "movie_directors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id"))
    Set<Director> directorSet;

    @ManyToMany
    @JoinTable(
            name = "movie_languages",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    Set<Language> languageSet;

    @ManyToMany
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    Set<Genre> genreSet;

    public Movie() {
        this.actorSet = new HashSet<>();
        this.directorSet = new HashSet<>();
        this.languageSet = new HashSet<>();
        this.genreSet = new HashSet<>();
    }

    public Movie(Long mid, String title, String description, String rated, Integer year, String posterUrlString, Date releaseDate, Double ratings, String type, PricingCategory pricingCategory){
        this.mid = mid;
        this.title = title;
        this.description = description;
        this.rated = rated;
        this.year = year;
        this.posterUrlString = posterUrlString;
        this.releaseDate = releaseDate;
        this.ratings = ratings;
        this.type = type;
        this.pricingCategory = pricingCategory;
        this.actorSet = new HashSet<>();
        this.directorSet = new HashSet<>();
        this.languageSet = new HashSet<>();
        this.genreSet = new HashSet<>();
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPosterUrlString() {
        return posterUrlString;
    }

    public void setPosterUrlString(String posterUrlString) {
        this.posterUrlString = posterUrlString;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getRatings() {
        return ratings;
    }

    public void setRatings(Double ratings) {
        this.ratings = ratings;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PricingCategory getPricingCategory() {
        return pricingCategory;
    }

    public void setPricingCategory(PricingCategory pricingCategory) {
        this.pricingCategory = pricingCategory;
    }

    public Set<Actor> getActorSet() {
        return actorSet;
    }

    public void setActorSet(Set<Actor> actorSet) {
        this.actorSet = actorSet;
    }

    public Set<Director> getDirectorSet() {
        return directorSet;
    }

    public void setDirectorSet(Set<Director> directorSet) {
        this.directorSet = directorSet;
    }

    public Set<Language> getLanguageSet() {
        return languageSet;
    }

    public void setLanguageSet(Set<Language> languageSet) {
        this.languageSet = languageSet;
    }

    public Set<Genre> getGenreSet() {
        return genreSet;
    }

    public void setGenreSet(Set<Genre> genreSet) {
        this.genreSet = genreSet;
    }

    public Double calculateCost(Integer numberOfDays){
        return this.pricingCategory.calculateCost(numberOfDays);
    }

    public void addToActorSet(Actor actor) {
        this.actorSet.add(actor);
    }

    public void addToDirectorSet(Director director) {
        this.directorSet.add(director);
    }

    public void addToLanguageSet(Language language) {
        this.languageSet.add(language);
    }

    public void addToGenreSet(Genre genre) {
        this.genreSet.add(genre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(mid, movie.mid) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(description, movie.description) &&
                Objects.equals(rated, movie.rated) &&
                Objects.equals(year, movie.year) &&
                Objects.equals(posterUrlString, movie.posterUrlString) &&
                Objects.equals(releaseDate, movie.releaseDate) &&
                Objects.equals(ratings, movie.ratings) &&
                Objects.equals(type, movie.type) &&
                Objects.equals(pricingCategory, movie.pricingCategory) &&
                Objects.equals(actorSet, movie.actorSet) &&
                Objects.equals(directorSet, movie.directorSet) &&
                Objects.equals(languageSet, movie.languageSet) &&
                Objects.equals(genreSet, movie.genreSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mid, title, description, rated, year, posterUrlString, releaseDate, ratings, type, pricingCategory, actorSet, directorSet, languageSet, genreSet);
    }
}
