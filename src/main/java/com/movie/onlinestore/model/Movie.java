package com.movie.onlinestore.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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

    public Movie() {
    }

    public Movie(Long mid, String title, String description, String rated, Integer year, String posterUrlString, Date releaseDate, Double ratings, String type, PricingCategory pricingCategory) {
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
}
