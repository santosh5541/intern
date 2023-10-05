package com.inventory.service.impl;

import com.inventory.dto.CartDTO;
import com.inventory.dto.ItemRequest;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.Cart;
import com.inventory.model.CartItem;
import com.inventory.model.Product;
import com.inventory.model.User;
import com.inventory.repository.CartRepository;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.UserRepository;
import com.inventory.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CartServiceImpl(UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CartDTO addItem(ItemRequest item, String userName) {
        int productId = item.getProductId();
        int quantity = item.getQuantity();
        // fetch users
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        // fetch Product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (!product.isStock()) {
            throw new ResourceNotFoundException("Product is out of stock");
        }
        // create product with productID and Quantity

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        double totalPrice = product.getProduct_prize() * quantity;
        cartItem.setTotalPrice(totalPrice);

        // getting cart from user
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }
        cartItem.setCart(cart);

        Set<CartItem> items = cart.getItems();
        // check if the item is available in cart items
        // if cart item is available, then increase the quantity only
        // otherwise, add a new cart item
        AtomicReference<Boolean> flag = new AtomicReference<>(false);
        Set<CartItem> newProducts = items.stream().map(i -> {
            if (i.getProduct().getProduct_id() == product.getProduct_id()) {
                i.setQuantity(quantity);
                i.setTotalPrice(totalPrice);
                flag.set(true);
            }
            return i;
        }).collect(Collectors.toSet());
        if (flag.get()) {
            items.clear();
            items.addAll(newProducts);
        } else {
            cartItem.setCart(cart);
            items.add(cartItem);
        }
        Cart savedCart = cartRepository.save(cart);

        return modelMapper.map(savedCart, CartDTO.class);
    }

    @Override
    public CartDTO getCartByID(int cartId) {
        Cart findByUserandCartId = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return modelMapper.map(findByUserandCartId, CartDTO.class);
    }

    @Override
    public CartDTO removeCartItemFromCart(String userName, int productId) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = user.getCart();
        Set<CartItem> items = cart.getItems();
        boolean removeIf = items.removeIf((i) -> i.getProduct().getProduct_id() == productId);
        Cart save = cartRepository.save(cart);
        return modelMapper.map(save, CartDTO.class);
    }




}
