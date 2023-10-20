package com.netmind.microservices.usermanagemeentservice.controllers;


import com.netmind.microservices.usermanagemeentservice.entites.Customer;
import com.netmind.microservices.usermanagemeentservice.payloads.ApiResponse;
import com.netmind.microservices.usermanagemeentservice.services.ICustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {

    @Autowired
    private ICustomerService ICustomerService;

    // Create
    @PostMapping
    public ResponseEntity<Customer> createUser(@Valid @RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ICustomerService.create(customer));
    }

    // Get all
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(ICustomerService.getAll());
    }


    // Get one
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomer(@Min(1) @PathVariable Long customerId) {

        return ResponseEntity.status(HttpStatus.OK).body(ICustomerService.get(customerId));

    }

    //delete
    @DeleteMapping("/{customerId}")
    public ApiResponse deleteCustomer(@Min(1) @PathVariable Long customerId) {

        this.ICustomerService.delete(customerId);
        return new ApiResponse(" Customer is Successfully Deleted !!", true);
    }

    //update
    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer, @Min(1) @PathVariable Long customerId) {

        return ResponseEntity.status(HttpStatus.OK).body(ICustomerService.update(customerId, customer));

    }

}
