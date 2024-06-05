package com.photon.order.client;

import com.photon.cart.entity.Cart;
import com.photon.cart.repository.CartRepositoryWrapper;
import com.photon.infrastructure.Response;
import com.photon.order.dto.PlaceOrderDTO;
import com.photon.order.request.PlaceOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderRestClient {

    private final RestTemplate restTemplate;

    private final CartRepositoryWrapper cartRepositoryWrapper;

    @Value("${com.photon.ecommerce.orderEndpoint}")
    private String orderServiceEndpoint;

    public Response placeOrder(PlaceOrderRequest request) {
        try {
            Cart cart = cartRepositoryWrapper.findActiveCartByUserId(request.getUserId()).orElseThrow(() -> new RuntimeException("Cart is empty"));
            String orderURI = orderServiceEndpoint + File.separator + "/placeOrder";
            final PlaceOrderDTO placeOrderDTO = new PlaceOrderDTO();
            ResponseEntity<Response> responseEntity = restTemplate.postForEntity(orderURI, placeOrderDTO, Response.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
