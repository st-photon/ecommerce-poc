package com.photon.cart.entity;


import com.photon.infrastructure.entity.BaseEntity;
import com.photon.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "Cart")
@Table(name = "cart")
@Getter
@Setter
public class Cart extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private List<CartItem> cartItems = new ArrayList<>();

    @Column(name = "is_checked_out", nullable = false)
    @Basic(optional = false)
    private boolean isCheckedOut;

    @Column(name = "is_deleted", nullable = false)
    @Basic(optional = false)
    private boolean isDeleted;

    @Column(name = "order_id")
    @JdbcType(VarcharJdbcType.class)
    private UUID orderId;
}
