package com.movie.onlinestore.controllers;


import com.movie.onlinestore.UrlConstants;
import com.movie.onlinestore.model.PlaceOrderRequest;
import com.movie.onlinestore.model.Response;
import com.movie.onlinestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(UrlConstants.URL_PATH_PLACE_ORDER)
    @ResponseBody
    public ResponseEntity placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest, @RequestAttribute(name = "user_id") String userId) {

        if (!orderService.isAddressValid(placeOrderRequest))
            return new ResponseEntity(new Response<String>(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    "Invalid address provided", ""), HttpStatus.OK);

        if (!orderService.isCartListValid(placeOrderRequest))
            return new ResponseEntity(new Response<String>(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    "Cart is empty", ""), HttpStatus.OK);

        List<Long> outOfStockMoviesIds = orderService.checkForMissingCartItems(placeOrderRequest);
        if (outOfStockMoviesIds.size() > 0) {
            return new ResponseEntity(new Response<OutOfStockResponse>(HttpStatus.ACCEPTED.value(),
                    "Some movies out of stock", new OutOfStockResponse(outOfStockMoviesIds)), HttpStatus.OK);
        }

        orderService.placeOrder(placeOrderRequest, userId);

        return new ResponseEntity<>(Response.success("Success"), HttpStatus.OK);
    }

    private static class OutOfStockResponse {
        private List<Long> outOfStockMovieIds;

        OutOfStockResponse(List<Long> outOfStockMovieIds) {
            this.outOfStockMovieIds = outOfStockMovieIds;
        }

        public List<Long> getOutOfStockMovieIds() {
            return outOfStockMovieIds;
        }
    }
}
