package com.example.springstarbucksapi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.Column;

import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Data
@Entity
public class StarbucksOrder {
    private @Id @GeneratedValue Long id;

    @Column(nullable=false) private String drink;
    @Column(nullable=false) private String milk;
    @Column(nullable=false) private String size;
    @Column(nullable=false) private double total;

    private String status;

    public StarbucksOrder() {}

    /**
     * Calculates a drink's total from its base price times the drink size multiplier.
     */
    public void setTotal() {
        for (Drink drink: Drink.values()) {
            if (this.drink.equals(drink.label)) {
                for (Size size: Size.values()) {
                    if (this.size.equals(size.label)) {
                        this.total = drink.basePrice * size.multiplier;
                        return;
                    }
                }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid size");
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid drink");
    }
}