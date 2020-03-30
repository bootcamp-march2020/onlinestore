package com.movie.onlinestore.repository;

import com.movie.onlinestore.model.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Optional;

public class LanguageRepositoryCustomImpl implements LanguageRepositoryCustom {

    @Autowired
    @Lazy
    LanguageRepository languageRepository;

    @Override
    public Language findOrCreate(String name) {
        Language language = null;
        Optional<Language> languageRecord = languageRepository.findByName(name);
        if(languageRecord.isPresent()){
            language = languageRecord.get();
        }
        else{
            language = new Language(null,name);
            language = languageRepository.save(language);
        }
        return language;
    }
}
