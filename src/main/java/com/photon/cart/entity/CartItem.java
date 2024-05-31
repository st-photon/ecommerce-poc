package com.photon.cart.entity;


import com.photon.infrastructure.entity.BaseEntity;
import com.photon.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "CartItem")
@Entity(name = "cart_item")
@Getter
@Setter
public class CartItem extends BaseEntity {

    @OneToOne(targetEntity = Product.class, optional = false)
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Column(name = "qty", nullable = false)
    @Basic(optional = false)
    private Long qty;

    @Column(name = "is_deleted", nullable = false)
    @Basic(optional = false)
    private boolean isDeleted;
}
