package com.photon.cart.services;


import com.photon.cart.request.AddCartItemRequest;
import com.photon.infrastructure.Response;

public interface CartCommandService {

    Response addToCart(AddCartItemRequest addCartItemRequest);
}
