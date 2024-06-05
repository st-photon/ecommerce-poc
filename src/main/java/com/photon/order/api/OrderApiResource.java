package com.photon.order.api;

import com.photon.infrastructure.Response;
import com.photon.order.client.OrderRestClient;
import com.photon.order.dto.PlaceOrderDTO;
import com.photon.order.request.PlaceOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderApiResource {

    private final OrderRestClient orderRestClient;

    @PostMapping(value = "/placeOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
        return this.orderRestClient.placeOrder(placeOrderRequest);
    }
}
