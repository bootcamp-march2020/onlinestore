package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("SELECT genre FROM Genre genre WHERE genre.name = ?1")
    Optional<Genre> findByName(String name);

}
