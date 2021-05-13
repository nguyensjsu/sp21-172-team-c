package com.example.backoffice_app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) private Integer id;
    private String firstName, lastName;
    private int rewards;
    private StarbucksCard card;
}
