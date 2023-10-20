package com.netmind.productsservice.controller;

import com.netmind.productsservice.model.Product;
import com.netmind.productsservice.persistence.ProductsRepository;
import com.netmind.productsservice.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductServiceController.class)
//@ActiveProfiles("tes_sin_db")
public class ProductServiceControllerTest_WebMvcTest {

    @BeforeEach
    public void setUp() {
        List<Product> products = Arrays.asList(
                new Product(1L, "Fake product", "")
        );

        Mockito.when(repository.findByNameContaining("Fake"))
                .thenReturn(products);

        Mockito.when(repository.findAll())
                .thenReturn(products);

        Mockito.when(repository.save(Mockito.any(Product.class)))
                .thenAnswer(elem -> {
                    Product ap = (Product) elem.getArguments()[0];
                    ap.setId(100L);
                    return ap;
                });
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductsRepository repository;

    @Test
    public void givenProducts_whenGetProducts_thenReturnJsonArray() throws Exception {

        /*Product aProduct = new Product(1L, "Fake product", "123-123-1234");

        List<Product> allProducts = Arrays.asList(aProduct);

        given(repository.findAll()).willReturn(allProducts);*/

        mvc.perform(get("/products?name=Fake")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].name", is(aProduct.getName())));
                .andExpect(jsonPath("$[0].name", is("Fake product")));
    }

    @Test
    void givenProducts_whenVaildCreateProduct_thenIsCreatedAndHaveId() throws Exception {
        Product newProduct = new Product(null, "Nuevo producto", "123-123-1234");

        mvc.perform(post("/products")
                        .content(JsonUtil.asJsonString(newProduct))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(100)));
    }

}