package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.ImportedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportedFileRepository extends JpaRepository<ImportedFile, Long> {
}
