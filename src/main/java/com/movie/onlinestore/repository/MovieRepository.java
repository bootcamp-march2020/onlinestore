package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = "select * from movie where mid in (select movie_id from movie_inventory where available_count > 0 and movie_id = :movieId ) ", nativeQuery = true)
    Movie findByIdIfHasStock(Long movieId);
}
