package com.netmind.microservices.ordersservice.controller;

import com.netmind.microservices.ordersservice.config.ConfigurationValues;
import com.netmind.microservices.ordersservice.model.Order;
import com.netmind.microservices.ordersservice.model.ProductBean;
import com.netmind.microservices.ordersservice.persistence.OrdersRepository;
import com.netmind.microservices.ordersservice.proxy.ProductsServiceClient;
import com.netmind.microservices.productsservice.model.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/orders")
public class OrderServiceController {

    @Autowired
    OrdersRepository orderRepo;

    @Autowired
    private ConfigurationValues limits;

    @Autowired
    ProductsServiceClient productsServiceClient;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createOrder(@RequestBody @Valid Order newOrder) {
        Integer quantity = newOrder.getQuantity();

        if (quantity >= limits.getMin() && quantity <= limits.getMax()) {
            ProductBean product = productsServiceClient.getProduct(newOrder.getProduct());
            newOrder.setFinalprice(product.getPrice() * newOrder.getQuantity());
            newOrder.setId(null);
            orderRepo.save(newOrder);
            if (newOrder != null && newOrder.getId() > 0) return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(new StatusMessage(HttpStatus.BAD_REQUEST.value(), "No encontrado"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new StatusMessage(HttpStatus.BAD_REQUEST.value(), "El pedido debe estar entre:" + limits.getMin() + " y " + limits.getMax()), HttpStatus.BAD_REQUEST);
        }
    }
}