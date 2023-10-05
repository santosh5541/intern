package com.inventory.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;
    private String product_name;
    private double product_prize;
    private boolean stock;
    private int product_quantity;
    private boolean live;
    private String product_imageName;
    private String product_desc;
    @ManyToOne(fetch = FetchType.EAGER)
    private Supplier supplier;



    @Override
    public int hashCode() {
        return Objects.hash(product_id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product other = (Product) obj;
        return Objects.equals(product_id, other.product_id);
    }
}
