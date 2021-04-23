package com.example.springstarbucksapi;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StarbucksOrderRepository extends JpaRepository<StarbucksOrder,Long> {
}