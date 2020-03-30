package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.MovieInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.util.Set;

@Repository
public interface MovieInventoryRepository extends JpaRepository<MovieInventory, Long>, JpaSpecificationExecutor<MovieInventory> {

    @Query(value = "UPDATE movie_inventory SET available_count  = available_count - 1 WHERE movie_id in :movieIds AND available_count > 0",nativeQuery = true)
    @Modifying
    @Transactional
    void updateMovieInventory(Set<Long> movieIds);
}

