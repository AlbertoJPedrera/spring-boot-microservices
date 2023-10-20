package com.netmind.productsservice.controller;

import com.netmind.productsservice.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

// TODO: uncomment and implement methods
@SpringBootTest
@Sql(value = "classpath:test.sql")
@Sql(value = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductServiceControllerTest {

    @Autowired
    private ProductServiceController controller;

    @Test
    @Order(2)
    void givenProducts_whengetAllProducts_thenIsNotNull() {
        ResponseEntity<List<Product>> productos = controller.getAll("");
        System.out.println("productos:" + productos.getBody());

        assertThat(productos.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(productos.getBody()).isNotNull();
        assertThat(productos.getBody().size()).isGreaterThanOrEqualTo(5);
    }

    @Test
    @Order(1)
    void givenProducts_whenVaildCreateProduct_thenIsCreatedAndHaveId() {
        Product newP = new Product(null, "new Product", "123-122-1234");
        ResponseEntity<Product> producto = controller.addProduct(newP);
        System.out.println("producto:" + producto.getBody());

        assertThat(producto.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
        Product rP = producto.getBody();
        assertThat(rP).isNotNull();
        assertThat(rP.getId()).isGreaterThan(0);
        assertThat(rP.getName()).isEqualTo(newP.getName());
    }
}