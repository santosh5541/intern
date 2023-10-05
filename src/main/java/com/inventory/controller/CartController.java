package com.inventory.controller;


import com.inventory.dto.CartDTO;
import com.inventory.dto.ItemRequest;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody ItemRequest itemRequest, Principal principal) {
        try {
            CartDTO addItem = cartService.addItem(itemRequest, principal.getName());
            return new ResponseEntity<>(addItem, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getCartById(@PathVariable int cartId){
        try {
            CartDTO cartDTO = cartService.getCartByID(cartId);
            return new ResponseEntity<>(cartDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{pId}")
    public ResponseEntity<CartDTO> deleteCartItemFromCart(@PathVariable int pId, Principal p) {
        CartDTO remove = cartService.removeCartItemFromCart(p.getName(), pId);
        return new ResponseEntity<>(remove, HttpStatus.OK);
    }

}
