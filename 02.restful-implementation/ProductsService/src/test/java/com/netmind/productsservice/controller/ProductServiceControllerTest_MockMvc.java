package com.netmind.productsservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmind.productsservice.ProductsServiceApplication;
import com.netmind.productsservice.model.Product;
import com.netmind.productsservice.persistence.ProductsRepository;
import com.netmind.productsservice.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


// TODO: uncomment and implement methods
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@Sql(value = "classpath:test.sql")
// @TestPropertySource( locations = "classpath:application-integrationtest.properties")
class ProductServiceControllerTest_MockMvc {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ProductsRepository repository;

    @Test
    public void givenProducts_whenGetProducts_thenStatus200() throws Exception {
        Product nuevoProd = new Product(null, "Nuevo Prod", "123-122-1223");
        repository.save(nuevoProd);

        mvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Nuevo Prod")));
//                .andExpect(jsonPath("$[*].name", hasItem("Nuevo Prod"))); // con @sql

    }

    @Test
    void givenProducts_whenValidCreateProduct_thenIsCreatedAndHaveId() throws Exception {
        Product nuevoProd = new Product(null, "Nuevo Prod", "123-122-1223");

        mvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.asJsonString(nuevoProd))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id", is(greaterThanOrEqualTo(1))));
    }

}