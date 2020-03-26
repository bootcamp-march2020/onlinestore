package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.MovieInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieInventoryRepository extends JpaRepository<MovieInventory, Long>, JpaSpecificationExecutor<MovieInventory> {
}
