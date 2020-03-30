package com.movie.onlinestore.model.response;

import java.util.List;

public class OutOfStockResponse {
    private List<Long> outOfStockMovieIds;

    public OutOfStockResponse(List<Long> outOfStockMovieIds) {
        this.outOfStockMovieIds = outOfStockMovieIds;
    }

    public List<Long> getOutOfStockMovieIds() {
        return outOfStockMovieIds;
    }
}