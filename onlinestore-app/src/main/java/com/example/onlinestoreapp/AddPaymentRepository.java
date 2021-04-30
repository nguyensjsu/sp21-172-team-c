package com.example.onlinestoreapp;

import org.springframework.data.jpa.repository.JpaRepository;

interface AddPaymentRepository extends JpaRepository<AddPaymentCommand,Long> {
    
}