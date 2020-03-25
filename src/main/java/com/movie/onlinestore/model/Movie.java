package com.movie.onlinestore.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long mid;
    @Column(name = "title") private String title;

    public Movie() {
        this.mid = null;
        this.title = null;
    }

    public Movie(Long mid, String title) {
        this.mid = mid;
        this.title = title;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return mid.equals(movie.mid) &&
                title.equals(movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mid, title);
    }
}
