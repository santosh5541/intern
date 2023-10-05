package com.inventory.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.model.OrderEntity;
import com.inventory.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private int orderItemId;

    private ProductDTO product;
    private double totalProductPrice;
    @JsonIgnore
    private OrderDTO order;


    @Override
    public int hashCode() {
        return Objects.hash(orderItemId, product, totalProductPrice);
    }


}
