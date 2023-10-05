package com.inventory.service;

import com.inventory.dto.CartDTO;
import com.inventory.dto.ItemRequest;

public interface CartService {
    public CartDTO addItem(ItemRequest item, String userName);
    public CartDTO getCartByID(int cartId);

    public CartDTO removeCartItemFromCart(String userName, int productId);
}
