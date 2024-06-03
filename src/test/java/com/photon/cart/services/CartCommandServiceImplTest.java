package com.photon.cart.services;

import com.photon.cart.entity.Cart;
import com.photon.cart.entity.CartItem;
import com.photon.cart.repository.CartItemRepository;
import com.photon.cart.repository.CartRepository;
import com.photon.cart.repository.CartRepositoryWrapper;
import com.photon.cart.request.AddCartItemRequest;
import com.photon.core.BaseUnitTest;
import com.photon.infrastructure.Response;
import com.photon.product.entity.Product;
import com.photon.product.entity.ProductRepositoryWrapper;
import com.photon.user.entity.User;
import com.photon.user.repository.UserRepositoryWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;


public class CartCommandServiceImplTest extends BaseUnitTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepositoryWrapper productRepositoryWrapper;

    @Mock
    private UserRepositoryWrapper userRepositoryWrapper;

    @Mock
    private CartRepositoryWrapper cartRepositoryWrapper;

    @InjectMocks
    private CartCommandServiceImpl cartCommandService;

    @Test
    @DisplayName("should create new cart")
    public void shouldCreateNewCart() {
        //arrange
        Mockito.when(productRepositoryWrapper.findById(Mockito.any())).thenReturn(Mockito.mock(Product.class));
        Mockito.when(userRepositoryWrapper.fetchById(Mockito.anyInt())).thenReturn(Mockito.mock(User.class));
        Mockito.when(cartRepositoryWrapper.findActiveCartByUserId(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(cartRepository.saveAndFlush(Mockito.any())).thenReturn(Mockito.mock(Cart.class));

        //act
        Response response = cartCommandService.addToCart(Mockito.mock(AddCartItemRequest.class));

        //assert
        Mockito.verify(productRepositoryWrapper, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(userRepositoryWrapper, Mockito.times(1)).fetchById(Mockito.anyInt());
        Mockito.verify(cartRepositoryWrapper, Mockito.times(1)).findActiveCartByUserId(Mockito.anyInt());
        Mockito.verify(cartRepository, Mockito.times(1)).saveAndFlush(Mockito.any());
        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("should update existing product qty in the existing cart")
    public void shouldRecalculateProductQty() {
        //arrange
        final UUID productId = UUID.fromString("6cc63550-f135-43fd-8b2b-cdf301417ff1");
        final Product product = new Product();
        product.setId(productId);
        Mockito.when(productRepositoryWrapper.findById(Mockito.any())).thenReturn(product);
        Mockito.when(userRepositoryWrapper.fetchById(Mockito.anyInt())).thenReturn(Mockito.mock(User.class));
        final Cart cart = new Cart();
        final CartItem cartItem = new CartItem();
        cartItem.setQty(2L);
        cartItem.setProduct(product);
        cart.getCartItems().add(cartItem);
        Mockito.when(cartRepositoryWrapper.findActiveCartByUserId(Mockito.anyInt())).thenReturn(Optional.of(cart));
        Mockito.when(cartRepository.saveAndFlush(Mockito.any())).thenReturn(Mockito.mock(Cart.class));

        //act
        Response response = cartCommandService.addToCart(Mockito.mock(AddCartItemRequest.class));

        //assert
        Mockito.verify(productRepositoryWrapper, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(userRepositoryWrapper, Mockito.times(1)).fetchById(Mockito.anyInt());
        Mockito.verify(cartRepositoryWrapper, Mockito.times(1)).findActiveCartByUserId(Mockito.anyInt());
        Mockito.verify(cartRepository, Mockito.times(1)).saveAndFlush(Mockito.any());
        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("should add new product in the existing cart")
    public void shouldAddItemToExistingCart() {
        //arrange
        final UUID productId = UUID.fromString("6cc63550-f135-43fd-8b2b-cdf301417ff1");
        final Product product = new Product();
        product.setId(productId);
        Mockito.when(productRepositoryWrapper.findById(Mockito.any())).thenReturn(product);
        Mockito.when(userRepositoryWrapper.fetchById(Mockito.anyInt())).thenReturn(Mockito.mock(User.class));
        final Cart cart = new Cart();
        cart.setId(UUID.fromString("5cc63550-f135-43fd-8b2b-cdf301417ff1"));
        Mockito.when(cartRepositoryWrapper.findActiveCartByUserId(Mockito.anyInt())).thenReturn(Optional.of(cart));
        Mockito.when(cartRepository.saveAndFlush(Mockito.any())).thenReturn(cart);

        //act
        Response response = cartCommandService.addToCart(Mockito.mock(AddCartItemRequest.class));

        //assert
        Mockito.verify(productRepositoryWrapper, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(userRepositoryWrapper, Mockito.times(1)).fetchById(Mockito.anyInt());
        Mockito.verify(cartRepositoryWrapper, Mockito.times(1)).findActiveCartByUserId(Mockito.anyInt());
        Mockito.verify(cartRepository, Mockito.times(1)).saveAndFlush(Mockito.any());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(UUID.fromString("5cc63550-f135-43fd-8b2b-cdf301417ff1"));
    }

    @Test
    @DisplayName("Remove Item from cart success scenario")
    public void removeItemFromCart() {
        //arrange
        final UUID productId = UUID.fromString("6cc63550-f135-43fd-8b2b-cdf301417ff1");
        final UUID cartId = UUID.fromString("5cc63550-f135-43fd-8b2b-cdf301417ff1");
        final Product product = new Product();
        product.setId(productId);
        Mockito.when(userRepositoryWrapper.fetchById(Mockito.anyInt())).thenReturn(Mockito.mock(User.class));
        final Cart cart = new Cart();
        cart.setId(cartId);
        final CartItem cartItem = new CartItem();
        cartItem.setId(UUID.randomUUID());
        cartItem.setProduct(product);
        cart.getCartItems().add(cartItem);
        Mockito.when(cartRepositoryWrapper.findActiveCartByUserId(Mockito.anyInt())).thenReturn(Optional.of(cart));
        Mockito.doNothing().when(cartItemRepository).deleteEntityById(cartItem.getId());

        //act
        this.cartCommandService.removeCartItem(anyInt(), productId);

        //assert
        Mockito.verify(cartItemRepository, Mockito.times(1)).deleteEntityById(Mockito.any());
    }

    @Test
    @DisplayName("should throw error when cart is empty during delete")
    public void shouldThrowErrorWhenCartEmptyDuringDelete() {
        try {
            //arrange
            Mockito.when(userRepositoryWrapper.fetchById(Mockito.anyInt())).thenReturn(Mockito.mock(User.class));
            Mockito.when(cartRepositoryWrapper.findActiveCartByUserId(Mockito.anyInt())).thenReturn(Optional.empty());

            //act
            this.cartCommandService.removeCartItem(anyInt(), UUID.randomUUID());
        } catch (RuntimeException e) {
            //assert
            assertThat(e.getMessage()).isEqualTo("Active cart not available for this user");
        }
    }

    @Test
    @DisplayName("should throw error when cart item empty during delete")
    public void shouldThrowErrorWhenCartItemIsEmptyDuringDelete() {
        try {
            //arrange
            Mockito.when(userRepositoryWrapper.fetchById(Mockito.anyInt())).thenReturn(Mockito.mock(User.class));
            final UUID cartId = UUID.fromString("5cc63550-f135-43fd-8b2b-cdf301417ff1");
            final Cart cart = new Cart();
            cart.setId(cartId);
            Mockito.when(cartRepositoryWrapper.findActiveCartByUserId(Mockito.anyInt())).thenReturn(Optional.of(cart));

            //act
            this.cartCommandService.removeCartItem(anyInt(), UUID.randomUUID());
        } catch (RuntimeException e) {
            //arrange
            assertThat(e.getMessage()).isEqualTo("Product not available for this user cart");
        }
    }
}
