package com.example.backoffice_app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
@Entity
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private Integer id;

    private String firstName, lastName;
    private int rewards;

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    } 

    public void setLastName(String lastName) {
        this.lastName = lastName;
    } 

    public int getRewards() {
        return this.rewards;
    }

    public void setTotalRewards(int rewards) {
        this.rewards = rewards;
    }

    public void addReward() {
        this.rewards++;
    }

    public void subtractReward() {
        this.rewards--;
    }

    
}
