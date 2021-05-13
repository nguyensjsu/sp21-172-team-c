package com.example.backoffice_app;

import org.springframework.data.repository.CrudRepository;
import com.example.backoffice_app.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}