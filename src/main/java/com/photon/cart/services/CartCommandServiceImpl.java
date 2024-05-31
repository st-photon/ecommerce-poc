package com.photon.cart.services;


import com.photon.cart.entity.Cart;
import com.photon.cart.entity.CartItem;
import com.photon.cart.repository.CartRepository;
import com.photon.cart.repository.CartRepositoryWrapper;
import com.photon.cart.request.AddCartItemRequest;
import com.photon.infrastructure.Response;
import com.photon.product.entity.Product;
import com.photon.product.entity.ProductRepositoryWrapper;
import com.photon.user.entity.User;
import com.photon.user.repository.UserRepositoryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartCommandServiceImpl implements CartCommandService {

    private final ProductRepositoryWrapper productRepositoryWrapper;

    private final CartRepository cartRepository;

    private final UserRepositoryWrapper userRepositoryWrapper;

    private final CartRepositoryWrapper cartRepositoryWrapper;

    @Override
    @Transactional
    public Response addToCart(AddCartItemRequest addCartItemRequest) {
        try {
            final Product product = this.productRepositoryWrapper.findById(addCartItemRequest.getProductId());
            final User user = userRepositoryWrapper.fetchById(addCartItemRequest.getUserId());
            final Optional<Cart> cartOptional = cartRepositoryWrapper.findActiveCartByUserId(user.getUserId());
            if(cartOptional.isEmpty()) {
                Cart cart = new Cart();
                cart.setCheckedOut(false);
                cart.setDeleted(false);
                cart.setUser(user);
                cart.getCartItems().add(createCartItem(product, addCartItemRequest.getQty()));
                final Cart newCart = this.cartRepository.saveAndFlush(cart);
                return Response.of(newCart.getId());
            }
            Cart cart = cartOptional.get();
            Optional<CartItem> cartItemOptional = cart.getCartItems()
                    .stream()
                    .filter(c -> c.getProduct().getId().equals(product.getId()))
                    .findFirst();
            if(cartItemOptional.isEmpty()) {
                cart.getCartItems().add(createCartItem(product, addCartItemRequest.getQty()));
            } else {
                cartItemOptional.get().setQty(addCartItemRequest.getQty());
            }
            cart = this.cartRepository.saveAndFlush(cart);
            return Response.of(cart.getId());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private CartItem createCartItem(Product product, Long qty) {
        final CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQty(qty);
        cartItem.setDeleted(false);
        return cartItem;
    }
}
