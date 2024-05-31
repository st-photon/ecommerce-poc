package com.photon.product.services;


import com.photon.product.dto.ProductDTO;
import com.photon.product.entity.Product;
import com.photon.product.entity.ProductRepository;
import com.photon.product.entity.ProductRepositoryWrapper;
import com.photon.product.helpers.ProductDTOHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    private final ProductDTOHelper productDTOHelper;

    private final ProductRepositoryWrapper productRepositoryWrapper;

    @Override
    public List<ProductDTO> fetchAll() {
        List<Product> products = this.productRepository.findAll();
        return this.productDTOHelper.map(products);
    }

    @Override
    public ProductDTO fetchById(UUID productId) {
        final Product product = this.productRepositoryWrapper.findById(productId);
        return this.productDTOHelper.map(product);
    }
}
