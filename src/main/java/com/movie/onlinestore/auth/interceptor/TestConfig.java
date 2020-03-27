package com.movie.onlinestore.auth.interceptor;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("test")
public class TestConfig implements WebMvcConfigurer {

}