package com.movie.onlinestore.auth.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * For testing purpose, some of the test need not to worry about passing the client-id-token (since it's verify with google)
 * I have created this class to bypass the {@link LoginInterceptor}.
 * If the test want to still leverage the {@link LoginInterceptor} then they can use
 * {@code MockMvcBuilders.webAppContextSetup(webApplicationContext).build();}} this MockMvc instance to do that
 */
@Configuration
@EnableWebMvc
public class MvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }
}
