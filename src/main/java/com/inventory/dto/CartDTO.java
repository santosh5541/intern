package com.inventory.dto;

import com.inventory.model.CartItem;
import com.inventory.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private int cartId;
    private Set<CartItemDTO> items = new HashSet<>();
    private UserDTO user;
    @Override
    public int hashCode() {
        return Objects.hash(cartId);
    }
}
