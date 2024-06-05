package com.photon.order.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderRequest {

    private int userId;

    private Address shippingAddress;
}
