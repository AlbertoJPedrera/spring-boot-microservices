package com.netmind.productsservice.service;

import com.netmind.productsservice.model.Product;
import com.netmind.productsservice.persistence.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    public List<Product> getProductsByText(String text) {
        return productsRepository.findByNameContaining(text);
    }
}
