package com.netmind.productsservice.controller;

import com.netmind.productsservice.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: uncomment and implement methods
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("classpath:test.sql")
public class ProductServiceTestRestTemplate {
    // @Value(value = "${local.server.port}")
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenUrl_whenGetProducts_thenAStringExists() throws Exception {
        ResponseEntity<String> response = restTemplate
                .getForEntity("http://localhost:" + port + "/api/products",
                        String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Magazine");
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    public void givenUrl_whenGetProducts_thenAProductExists() throws Exception {
        ResponseEntity<Product[]> response = restTemplate
                .getForEntity("http://localhost:" + port + "/api/products",
                        Product[].class);

        Product productToTest = new Product(1L, "Magazine", "111-222-3333");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

        Product[] responseBody = response.getBody();
        assertThat(responseBody)
                .overridingErrorMessage(
                        "Expected the array to contain the object: %s in any order but it contained: %s",
                        productToTest,
                        Arrays.toString(responseBody)
                )
                .extracting(Product::getName)
                .contains("Magazine");
    }

    @Test
    public void givenAProduct_whenPostWithHeader_thenSuccess() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/api/products";
        URI uri = new URI(baseUrl);
        Product product = new Product(null, "New Test Product", "111-222-3333");

        HttpHeaders headers = new HttpHeaders();
        headers.set("ACCEPT", "application/xml");

        HttpEntity<Product> request = new HttpEntity<>(product, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        System.out.println(result);

        //Verify request succeed
        assertThat(result.getStatusCodeValue()).isEqualTo(201);
    }

}
