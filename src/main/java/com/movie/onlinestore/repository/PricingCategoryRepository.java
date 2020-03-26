package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.PricingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingCategoryRepository extends JpaRepository<PricingCategory, Long> {
}
