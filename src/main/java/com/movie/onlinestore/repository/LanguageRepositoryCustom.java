package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.Language;

public interface LanguageRepositoryCustom {

    public Language findOrCreate(String name);

}
