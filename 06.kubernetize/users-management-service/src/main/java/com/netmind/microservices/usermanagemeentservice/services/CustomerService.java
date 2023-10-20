package com.netmind.microservices.usermanagemeentservice.services;

import com.netmind.microservices.usermanagemeentservice.entites.Customer;
import com.netmind.microservices.usermanagemeentservice.exceptions.ResourceNotFoundException;
import com.netmind.microservices.usermanagemeentservice.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    private Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Override
    public Customer create(Customer customer) {
        logger.info("customer:" + customer);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer get(Long id) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with given id not found"));

        return customer;
    }

    @Override
    public Customer update(Long id, Customer customer) {
        Customer customer1 = this.customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with given id not found"));
        customer1.setName(customer.getName());
        customer1.setEmail(customer.getEmail());
        customer1.setPassword(customer.getPassword());

        return customerRepository.save(customer1);
    }

    @Override
    public void delete(Long id) {
        Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with given id not found"));
        // Deleting Accounts from ACCOUNT-SERVICE

        this.customerRepository.delete(customer);
    }
}
