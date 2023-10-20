package com.netmind.productsservice.controller;

import com.netmind.productsservice.exception.ProductNotfoundException;
import com.netmind.productsservice.exception.ProductNotfoundExceptionHijo;
import com.netmind.productsservice.model.Product;
import com.netmind.productsservice.model.StatusMessage;
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
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Validated
//@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@Tag(name = "Products API", description = "API de los productos")
public class ProductServiceController {

    @Autowired
    public ProductsRepository repo;

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceController.class);

    /*@RequestMapping(path = "",method = RequestMethod.GET)
    public List<Product> getAll(){
        return repo.findAll();
    }*/

    //    @RequestMapping(path = "", method = RequestMethod.GET)
    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Product>> getAll(@RequestParam(defaultValue = "") String name) {
//        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        return new ResponseEntity<>(repo.findByNameContaining(name), HttpStatus.OK);
    }

    //    @RequestMapping(path = "/{pid}", method = RequestMethod.GET)
    @GetMapping("/{pid}")
    @Valid
    @Operation(summary = "Get a product by id", description = "Returns a product as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public ResponseEntity<Product> getAProduct(
            @Parameter(name = "pid", description = "Product id", example = "1")
            @PathVariable(name = "pid") @Min(1) Long id
    ) {
        Optional<Product> prod = repo.findById(id);
        if (!prod.isEmpty()) return new ResponseEntity<>(prod.get(), HttpStatus.OK);
        else throw new ProductNotfoundExceptionHijo(id);
//        else return new ResponseEntity<>(new StatusMessage(1289, "No existe"), HttpStatus.NOT_FOUND);
    }

    //    @RequestMapping(path = "", method = RequestMethod.POST)
    @PostMapping(value = "",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @Operation(summary = "Add a new product", description = "Returns a presisted product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully created"),
            @ApiResponse(responseCode = "4XX", description = "Bad request")
    })
    public ResponseEntity<Product> addProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody( required = true, description = "Product data")
            @RequestBody @Valid Product newP
    ) {
        newP.setId(null);
        repo.save(newP);
        return new ResponseEntity(newP, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product aProduct) {
        aProduct.setId(id);
        repo.save(aProduct);
        return new ResponseEntity(aProduct, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
//        return new ResponseEntity<>(new StatusMessage(202, "El contenido se eliminó correctametne"), HttpStatus.ACCEPTED);
    }

}