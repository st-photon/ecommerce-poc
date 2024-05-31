package com.photon.cart.helpers;

import com.photon.cart.entity.CartItem;
import com.photon.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class CartCalculatorHelperTest {

    @InjectMocks
    private CartCalculatorHelper cartCalculatorHelper;

    @Test
    @DisplayName("should calculate cart subtotal")
    public void shouldCalculateSubTotal(){
        //arrange
        List<CartItem> cartItemList = new ArrayList<>();

        Product product = new Product();
        product.setProductName("product");
        product.setProductPrice("30");
        CartItem cartItem = new CartItem();
        cartItem.setQty(2L);
        cartItem.setProduct(product);
        cartItemList.add(cartItem);

        Product product1 = new Product();
        product1.setProductName("product1");
        product1.setProductPrice("20");
        CartItem cartItem1 = new CartItem();
        cartItem1.setQty(2L);
        cartItem1.setProduct(product1);
        cartItemList.add(cartItem1);

        //act
        BigDecimal subTotal = cartCalculatorHelper.calculateSubtotal(cartItemList);

        //assert
        assertThat(subTotal, is(BigDecimal.valueOf(100.0)));
    }

    @Test
    @DisplayName("should calculate cart total qty")
    public void shouldCalculateTotalQty() {
        //arrange
        List<CartItem> cartItemList = new ArrayList<>();

        CartItem cartItem = new CartItem();
        cartItem.setQty(2L);
        cartItemList.add(cartItem);

        CartItem cartItem1 = new CartItem();
        cartItem1.setQty(2L);
        cartItemList.add(cartItem1);

        //act
        Long totalQty = cartCalculatorHelper.calculateTotalQty(cartItemList);

        //assert
        assertThat(totalQty, is(4L));
    }

    @Test
    @DisplayName("should return cart total qty as zero")
    public void shouldReturnTotalQtyAsZero() {
        //arrange
        List<CartItem> cartItemList = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItemList.add(cartItem);

        CartItem cartItem1 = new CartItem();
        cartItemList.add(cartItem1);

        //act
        Long totalQty = cartCalculatorHelper.calculateTotalQty(cartItemList);

        //assert
        assertThat(totalQty, is(0L));
    }

    @Test
    @DisplayName("should ignore qty null when calculating total qty")
    public void shouldIgnoreNullWhenCalculatingTotalQty() {
        //arrange
        List<CartItem> cartItemList = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItemList.add(cartItem);

        CartItem cartItem1 = new CartItem();
        cartItem1.setQty(4L);
        cartItemList.add(cartItem1);

        //act
        Long totalQty = cartCalculatorHelper.calculateTotalQty(cartItemList);

        //assert
        assertThat(totalQty, is(4L));
    }
}
