package com.movie.onlinestore.controllers;

import com.movie.onlinestore.UrlConstants;
import com.movie.onlinestore.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping(UrlConstants.URL_PATH_UNAUTHORISED)
    public ResponseEntity<Response<String>> handleUnauthorized() {
        return new ResponseEntity<>(new Response<>(HttpStatus.UNAUTHORIZED.value(),
                "Missing authorisation token", ""), HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(UrlConstants.URL_PATH_INVALID_TOKEN)
    public ResponseEntity<Response<String>> handleInvalidIdToken() {
        return new ResponseEntity<>(new Response<>(HttpStatus.NOT_ACCEPTABLE.value(),
                "Invalid authorisation token", ""), HttpStatus.NOT_ACCEPTABLE);
    }
}
