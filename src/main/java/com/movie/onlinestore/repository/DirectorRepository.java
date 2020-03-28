package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

    @Query("SELECT director FROM Director director WHERE director.name = ?1")
    Optional<Director> findByName(String name);

}
