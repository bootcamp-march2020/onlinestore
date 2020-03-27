package com.movie.onlinestore.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DecimalFormat;

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

    public void setInitalCost(Double initalCost) {
        this.initalCost = initalCost;
    }

    public void setCutoffDays(Integer cutoffDays) {
        this.cutoffDays = cutoffDays;
    }

    public void setAdditionalCost(Double additionalCost) {
        this.additionalCost = additionalCost;
    }

    public String getInitialCostString() {
        DecimalFormat format = new DecimalFormat("0.###");
        return String.format("$%s for %d days",format.format(this.initalCost),this.cutoffDays);
    }

    public String getAdditionalCostString() {
        DecimalFormat format = new DecimalFormat("0.###");
        return String.format("and $%s afterwards",format.format(this.additionalCost));
    }

    public Double calculateCost(Integer numberOfDays){
        Double cost = this.initalCost;
        if(numberOfDays > this.cutoffDays){
            Integer remainingDays = numberOfDays - this.cutoffDays;
            cost += (remainingDays * this.additionalCost);
        }
        return cost;
    }
}
