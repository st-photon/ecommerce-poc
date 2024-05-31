package com.photon.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDTO implements Serializable {

    @JsonProperty("sub_total")
    private BigDecimal subTotal = BigDecimal.ZERO;

    @JsonProperty("items")
    private List<CartItemDTO> cartItemDTOList;
}
