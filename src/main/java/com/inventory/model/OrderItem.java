package com.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    @OneToOne
    private Product product;
    private double totalProductPrice;
    private int productQuantity;

    @ManyToOne
    private OrderEntity order;


    @Override
    public int hashCode() {
        return Objects.hash(orderItemId, product, totalProductPrice, productQuantity);
    }
}
