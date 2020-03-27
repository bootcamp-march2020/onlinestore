package com.movie.onlinestore.model;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long orderItemId;

    @Column (name = "order_id")
    private Long orderId;

    @OneToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @Column(name = "initial_cost")
    private Double initialCost;

    @Column(name = "cut_off_days")
    private  Integer cutOffDays;

    @Column(name = "additional_cost")
    private Double additionalCost;

    @Column(name = "total_cost")
    private Double totalCost;

    public OrderItem() {
    }

    public OrderItem( Long orderId, Movie movie, Integer numberOfDays,
                     Double initialCost, Double additionalCost, Integer cutOffDays, Double totalCost) {

        this.orderId = orderId;
        this.movie = movie;
        this.numberOfDays = numberOfDays;
        this.additionalCost = additionalCost;
        this.initialCost = initialCost;
        this.cutOffDays = cutOffDays;
        this.totalCost = totalCost;
    }


    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Double getInitialCost() {
        return initialCost;
    }

    public void setInitialCost(Double initialCost) {
        this.initialCost = initialCost;
    }

    public Integer getCutOffDays() {
        return cutOffDays;
    }

    public void setCutOffDays(Integer cutOffDays) {
        this.cutOffDays = cutOffDays;
    }

    public Double getAdditionalCost() {
        return additionalCost;
    }

    public void setAdditionalCost(Double additionalCost) {
        this.additionalCost = additionalCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}
