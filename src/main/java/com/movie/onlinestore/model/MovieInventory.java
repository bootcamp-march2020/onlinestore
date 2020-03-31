package com.movie.onlinestore.model;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Table(name = "movie_inventory")
public class MovieInventory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column (name = "available_count")
    private Integer availableCount;

    @Column (name = "total_count")
    private Integer totalCount;

    public MovieInventory() {
    }

    public MovieInventory(Long id, Movie movie, Integer availableCount, Integer totalCount) {
        this.id = id;
        this.availableCount = availableCount;
        this.movie = movie;
        this.totalCount = totalCount;
    }

    public MovieInventory(Movie movie, Integer totalCount) {
        this.movie = movie;
        this.totalCount = totalCount;
        this.availableCount = totalCount;
    }

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Integer getAvailableCount() {
        return availableCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void addStock(Integer stockCount) {
        this.totalCount += stockCount;
        this.availableCount += stockCount;
    }
}
