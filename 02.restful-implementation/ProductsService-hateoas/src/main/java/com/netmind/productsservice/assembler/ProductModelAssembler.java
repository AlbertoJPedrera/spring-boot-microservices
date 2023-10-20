package com.netmind.productsservice.assembler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.netmind.productsservice.controller.ProductServiceController;
import com.netmind.productsservice.model.Product;
import com.netmind.productsservice.dto.ProductDTO;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Component;

@Component
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<Product, ProductDTO> {
    public ProductModelAssembler() {
        super(ProductServiceController.class, ProductDTO.class);
    }

    @Override
    public ProductDTO toModel(Product entity) {
        ProductDTO productModel = instantiateModel(entity);

        productModel.add(linkTo(
                methodOn(ProductServiceController.class)
                        .getProduct(entity.getId()))
                .withSelfRel());

        productModel.setId(entity.getId());
        productModel.setName(entity.getName());
        productModel.setSerial(entity.getSerial());
        return productModel;
    }

    public List<ProductDTO> toProductModel(List<Product> products) {
        if (products.isEmpty())
            return Collections.emptyList();

        return products.stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .build()
                        .add(linkTo(
                                methodOn(ProductServiceController.class)
                                        .getProduct(product.getId()))
                                .withSelfRel()))
                .collect(Collectors.toList());
    }
}
