package com.example.springstarbucksapi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.Column;

import lombok.Data;

@Data
@Entity
@Table(indexes=@Index(name="altIndex", columnList="cardNumber", unique=true))
public class StarbucksCard {
    private @Id @GeneratedValue Long id;
    
    @Column(nullable=false) private String cardNumber;
    @Column(nullable=false) private String cardCode;
    @Column(nullable=false) private double balance;
    @Column(nullable=false) private boolean activated;
    
    private String status;

    StarbucksCard() {}
}