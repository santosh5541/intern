package com.inventory.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private int cartItemId;
    private int quantity;
    private double totalPrice;
    @JsonIgnore
    private CartDTO cart;
    private ProductDTO product;


    @Override
    public int hashCode() {
        return Objects.hash(cartItemId);
    }
}
