package com.photon.cart.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.photon.cart.dto.CartDTO;
import com.photon.cart.dto.CartItemDTO;
import com.photon.cart.services.CartCommandService;
import com.photon.cart.services.CartQueryService;
import com.photon.core.BaseUnitTest;
import com.photon.product.dto.ProductDTO;
import com.photon.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@WebMvcTest(CartApiResource.class)
public class CartApiResourceTest extends BaseUnitTest {

    @MockBean
    private CartCommandService cartCommandService;

    @MockBean
    private CartQueryService cartQueryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should list my cart details")
    public void shouldShowCartList() throws Exception {
        //arrange
        CartDTO cartDTO = new CartDTO();
        cartDTO.setTotalQty(4L);
        cartDTO.setSubTotal(BigDecimal.valueOf(200));
        cartDTO.getCartItemDTOList().add(Mockito.mock(CartItemDTO.class));
        Mockito.when(cartQueryService.fetchMyCart(anyInt())).thenReturn(cartDTO);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/carts")
                .param("userId", "10")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        //act
        MockHttpServletResponse mvcResult = this.mockMvc.perform(requestBuilder).andReturn().getResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        CartDTO responseDto = objectMapper.readValue(mvcResult.getContentAsString(), CartDTO.class);

        //assert
        assertThat(mvcResult.getStatus()).isEqualTo(200);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getSubTotal()).isEqualTo(200);
        assertThat(responseDto.getCartItemDTOList()).isEqualTo(1);
        assertThat(responseDto.getTotalQty()).isEqualTo(4);
    }
}
