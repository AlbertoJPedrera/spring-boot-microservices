package com.netmind.microservices.usermanagemeentservice.repositories;

import com.netmind.microservices.usermanagemeentservice.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
