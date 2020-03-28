package com.movie.onlinestore;

public class UrlConstants {

    public static final String URL_PATH_UNAUTHORISED = "/public/error/401";

    public static final String URL_PATH_INVALID_TOKEN = "/public/error/406";

    public static final String URL_PATH_MOVIE_LIST = "/api/movies";

    public static final String URL_PATH_CART_CHECKOUT = "/api/checkout";

    public static final String URL_PATH_IMPORT_DATA = "/public/api/import";

    private UrlConstants(){
        //Utils class object creation forbidden.
    }
}
