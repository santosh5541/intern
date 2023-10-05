package com.inventory.repository;

import com.inventory.model.Cart;
import com.inventory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    public Optional<Cart> findByUser(User user);
}
