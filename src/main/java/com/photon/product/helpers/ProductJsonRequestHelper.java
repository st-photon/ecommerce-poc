package com.photon.product.helpers;


import com.photon.product.entity.Product;
import com.photon.product.request.CreateProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductJsonRequestHelper {

    public Product toEntity(CreateProductRequest createProductRequest) {
        final Product product = new Product();
        product.setProductName(createProductRequest.getName());
        product.setProductPrice(createProductRequest.getPrice());
        product.setDescription(createProductRequest.getDescription());
        product.setBrand(createProductRequest.getBrand());
        return product;
    }
}
