package com.netmind.productsservice.controller;

import com.netmind.productsservice.assembler.ProductModelAssembler;
import com.netmind.productsservice.model.Product;
import com.netmind.productsservice.exception.ProductNotfoundException;
import com.netmind.productsservice.dto.ProductDTO;
import com.netmind.productsservice.dto.StatusMessage;
import com.netmind.productsservice.persistence.ProductsRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
// @CrossOrigin(origins = {"*"}, allowedHeaders = "*")
@Tag(name = "Products API", description = "Products management APIs")
@Validated
public class ProductServiceController {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceController.class);

    @Autowired
    ProductsRepository productsRepo;

    @Autowired
    private ProductModelAssembler productModelAssembler;

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> productEntities = productsRepo.findAll();
        System.out.println("productEntities:"+productEntities);
        return new ResponseEntity<>(productModelAssembler.toProductModel(productEntities)
                , HttpStatus.OK);
    }

    @Operation(summary = "Get a product by id", description = "Returns a product as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ProductDTO> getProduct(
            @Parameter(name = "id", description = "Product id", example = "1") @PathVariable @Min(1) Long id
    ) {
        if (!productsRepo.existsById(id)) throw new ProductNotfoundException();
        return productsRepo.findById(id)
                .map(productModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createProduct(@RequestBody @Valid Product newProduct) {
//        newProduct.setId(null);
        productsRepo.save(newProduct);
        if (newProduct != null && newProduct.getId() > 0)
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(new StatusMessage(HttpStatus.BAD_REQUEST.value(), "No encontrado"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProduct(@PathVariable @Min(1) Long id, @RequestBody @Valid Product aProduct) {
        aProduct.setId(id);
        productsRepo.save(aProduct);
        if (aProduct != null) return new ResponseEntity(aProduct, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(new StatusMessage(HttpStatus.NOT_MODIFIED.value(), "No modificado"), HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteProduct(@PathVariable @Min(1) Long id) {
        productsRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}