package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long>, LanguageRepositoryCustom {

    @Query("SELECT language FROM Language language WHERE language.name = ?1")
    Optional<Language> findByName(String name);

}
