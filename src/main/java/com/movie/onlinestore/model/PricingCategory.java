package com.movie.onlinestore.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "pricing_caregory")
public class PricingCategory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "initial_cost")
    private Double initalCost;

    @Column(name = "cutoff_days")
    private Integer cutoffDays;

    @Column(name = "additional_cost")
    private Double additionalCost;

    public PricingCategory() {
    }

    public PricingCategory(Long id,Double initalCost,Integer cutoffDays,Double additionalCost) {
        this.id = id;
        this.name = name;
        this.initalCost = initalCost;
        this.cutoffDays = cutoffDays;
        this.additionalCost = additionalCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getInitalCost() {
        return initalCost;
    }

    public void setInitalCost(Double initalCost) {
        this.initalCost = initalCost;
    }

    public Integer getCutoffDays() {
        return cutoffDays;
    }

    public void setCutoffDays(Integer cutoffDays) {
        this.cutoffDays = cutoffDays;
    }

    public Double getAdditionalCost() {
        return additionalCost;
    }

    public void setAdditionalCost(Double additionalCost) {
        this.additionalCost = additionalCost;
    }
}
