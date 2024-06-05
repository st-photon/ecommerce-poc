package com.photon.order.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private String address1;

    private String address2;

    private String address3;

    private String zipCode;

    private String district;

    private String state;

    private String contactNumber;

    private String comment;

    private String addressType;
}
