package com.photon.product.helpers;


import com.photon.product.dto.ProductDTO;
import com.photon.product.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductDTOHelper {

    public List<ProductDTO> map(final List<Product> productList) {
        return productList.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public ProductDTO map(final Product product) {
        return ProductDTO
                .builder()
                .id(product.getId())
                .name(product.getProductName())
                .price(product.getProductPrice())
                .description(product.getDescription())
                .brand(product.getBrand())
                .build();
    }
}
