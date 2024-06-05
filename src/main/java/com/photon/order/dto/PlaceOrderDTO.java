package com.photon.order.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlaceOrderDTO {

    private Double subTotal;

    private Long totalQty;

    private List<PlaceOrderItemRequest> items = new ArrayList<>();

    private CustomerRequest customerRequest;

    @Data
    public static class PlaceOrderItemRequest {

        private String brand;

        private Long qty;

        private String description;

        private String name;

        private double price;
    }

    @Data
    public static class CustomerRequest {

        private String firstName;

        private String middleName;

        private String lastName;

        private String address1;

        private String address2;

        private String address3;

        private String contactNumber;

        private String mobileNumber;

        private String zipCode;

        private String district;

        private String state;

        private String additionalComment;

        private String addressType;
    }
}
