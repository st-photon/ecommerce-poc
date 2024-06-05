package com.photon.order.client;

import com.photon.cart.entity.Cart;
import com.photon.cart.entity.CartItem;
import com.photon.cart.helpers.CartCalculatorHelper;
import com.photon.cart.repository.CartRepository;
import com.photon.cart.repository.CartRepositoryWrapper;
import com.photon.infrastructure.Response;
import com.photon.order.dto.OrderResponseDTO;
import com.photon.order.dto.PlaceOrderDTO;
import com.photon.order.request.Address;
import com.photon.order.request.PlaceOrderRequest;
import com.photon.user.entity.User;
import com.photon.user.repository.UserRepositoryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderRestClient {

    private final RestTemplate restTemplate;

    private final CartRepositoryWrapper cartRepositoryWrapper;

    private final CartRepository cartRepository;

    private final CartCalculatorHelper cartCalculatorHelper;

    private final UserRepositoryWrapper userRepositoryWrapper;

    @Value("${com.photon.ecommerce.orderEndpoint}")
    private String orderServiceEndpoint;

    @Transactional
    public Response placeOrder(PlaceOrderRequest request) {
        try {
            final User user = this.userRepositoryWrapper.fetchById(request.getUserId());
            Cart cart = cartRepositoryWrapper.findActiveCartByUserId(request.getUserId()).orElseThrow(() -> new RuntimeException("Cart is empty"));
            String orderURI = orderServiceEndpoint + "/placeOrder";
            final PlaceOrderDTO placeOrderDTO = toDTO(user, cart, request);
            ResponseEntity<Response> responseEntity = restTemplate.postForEntity(orderURI, placeOrderDTO, Response.class);
            Response response = responseEntity.getBody();
            if(response == null) {
                throw new IllegalArgumentException("Response must not be empty");
            }
            cart.setOrderId(UUID.fromString((String)response.getId()));
            cart.setCheckedOut(true);
            cart = this.cartRepository.saveAndFlush(cart);
            return Response.of(cart.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private PlaceOrderDTO toDTO(User user,Cart cart, PlaceOrderRequest placeOrderRequest) {
        final PlaceOrderDTO placeOrderDTO = new PlaceOrderDTO();
        BigDecimal bigDecimal = cartCalculatorHelper.calculateSubtotal(cart.getCartItems());
        placeOrderDTO.setSubTotal(bigDecimal.doubleValue());
        placeOrderDTO.setTotalQty(cartCalculatorHelper.calculateTotalQty(cart.getCartItems()));
        placeOrderDTO.setCustomerRequest(createCustomerRequest(user,placeOrderRequest));
        placeOrderDTO.setItems(createItemRequestList(cart));
        placeOrderDTO.setUserId(user.getUserId());
        return placeOrderDTO;
    }

    private List<PlaceOrderDTO.PlaceOrderItemRequest> createItemRequestList(Cart cart) {
        return cart.getCartItems().stream().map(this::createItemRequest).toList();
    }

    private PlaceOrderDTO.PlaceOrderItemRequest createItemRequest(CartItem cartItem) {
        final PlaceOrderDTO.PlaceOrderItemRequest placeOrderItemRequest = new PlaceOrderDTO.PlaceOrderItemRequest();
        placeOrderItemRequest.setBrand(cartItem.getProduct().getBrand());
        placeOrderItemRequest.setQty(cartItem.getQty());
        placeOrderItemRequest.setPrice(Double.parseDouble(cartItem.getProduct().getProductPrice()));
        placeOrderItemRequest.setDescription(cartItem.getProduct().getDescription());
        placeOrderItemRequest.setName(cartItem.getProduct().getProductName());
        return placeOrderItemRequest;
    }

    private PlaceOrderDTO.CustomerRequest createCustomerRequest(User user, PlaceOrderRequest placeOrderRequest) {
        PlaceOrderDTO.CustomerRequest customerRequest = new PlaceOrderDTO.CustomerRequest();
        Address address = placeOrderRequest.getShippingAddress();
        customerRequest.setAdditionalComment(address.getComment());
        customerRequest.setAddress2(address.getAddress2());
        customerRequest.setAddress1(address.getAddress1());
        customerRequest.setAddress3(address.getAddress3());
        customerRequest.setAddressType(address.getAddressType());
        customerRequest.setContactNumber(address.getContactNumber());
        customerRequest.setState(address.getState());
        customerRequest.setDistrict(address.getDistrict());
        customerRequest.setZipCode(address.getZipCode());
        customerRequest.setMobileNumber(address.getContactNumber());
        customerRequest.setFirstName(user.getUserFirstName());
        customerRequest.setLastName(user.getUserLastName());
        return customerRequest;
    }

    public List<OrderResponseDTO> fetchMyOrders(int userId) {
        try {
            String orderURI = orderServiceEndpoint;
            UriComponents builder = UriComponentsBuilder.fromHttpUrl(orderURI)
                    .queryParam("userId",userId).build();
            ResponseEntity<List<OrderResponseDTO>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {});
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public OrderResponseDTO fetchMOrderDetails(UUID orderId, int userId) {
        try {
            String orderURI = orderServiceEndpoint;
            UriComponents builder = UriComponentsBuilder.fromHttpUrl(orderURI)
                    .queryParam("userId",userId).build();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
