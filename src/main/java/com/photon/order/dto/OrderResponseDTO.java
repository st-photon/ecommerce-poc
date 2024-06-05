package com.photon.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {

    private Double subTotal;

    private Long totalQty;

    private Double grandTotal;

    private String orderStatus;

    @JsonProperty("items")
    private List<OrderItemDTO> items = new ArrayList<>();

    @Getter
    @Setter
    public static class OrderItemDTO {

        private Long qty;

        private String name;

        private String brand;

        private String description;

        private Double price;
    }
}
