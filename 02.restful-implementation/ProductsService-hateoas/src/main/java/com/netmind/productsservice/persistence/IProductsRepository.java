package com.netmind.productsservice.persistence;

import com.netmind.productsservice.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductsRepository {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByNameContaining(String name);

    Product findByName(String name);

    Product save(Product aProduct);

    void deleteById(Long id);
}
