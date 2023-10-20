package com.netmind.microservices.usermanagemeentservice.services;


import com.netmind.microservices.usermanagemeentservice.entites.Customer;

import java.util.List;

public interface ICustomerService {

    // create

    Customer create(Customer customer);


    // get all

    List<Customer> getAll();

    //get single

    Customer get(Long id);


    //update

    Customer update(Long id, Customer customer);

    //delete

    void delete(Long id);



}
