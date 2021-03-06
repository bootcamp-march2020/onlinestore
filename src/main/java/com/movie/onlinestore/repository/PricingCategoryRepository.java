package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.PricingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PricingCategoryRepository extends JpaRepository<PricingCategory, Long> {

    @Query("SELECT pricingCategory FROM PricingCategory pricingCategory WHERE pricingCategory.name = ?1")
    Optional<PricingCategory> findByName(String name);

}
