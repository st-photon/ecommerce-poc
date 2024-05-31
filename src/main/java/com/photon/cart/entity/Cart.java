package com.photon.cart.entity;


import com.photon.infrastructure.entity.BaseEntity;
import com.photon.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Cart")
@Table(name = "cart")
@Getter
@Setter
public class Cart extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();

    @Column(name = "is_checked_out", nullable = false)
    @Basic(optional = false)
    private boolean isCheckedOut;

    @Column(name = "is_deleted", nullable = false)
    @Basic(optional = false)
    private boolean isDeleted;
}
