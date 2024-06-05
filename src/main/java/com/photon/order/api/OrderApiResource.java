package com.photon.order.api;

import com.photon.infrastructure.Response;
import com.photon.order.client.OrderRestClient;
import com.photon.order.dto.OrderResponseDTO;
import com.photon.order.request.PlaceOrderRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/orders")
@Tag(name = "OrderApiResource")
public class OrderApiResource {

    private final OrderRestClient orderRestClient;

    @PostMapping(value = "/placeOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
        return this.orderRestClient.placeOrder(placeOrderRequest);
    }

    @GetMapping
    public List<OrderResponseDTO> fetchMyOrders(@RequestParam(value = "userId") int userId){
        return this.orderRestClient.fetchMyOrders(userId);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDTO fetchMyOrders(@PathVariable UUID orderId, @RequestParam(value = "userId") int userId){
        return new OrderResponseDTO();
    }
}
