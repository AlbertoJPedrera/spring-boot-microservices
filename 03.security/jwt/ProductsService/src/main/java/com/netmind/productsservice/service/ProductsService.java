package com.netmind.productsservice.service;

import com.netmind.productsservice.model.Product;
import com.netmind.productsservice.persistence.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    public List<Product> getProductsByText(String text) {
        return productsRepository.findByNameContaining(text);
    }
}
