package com.inventory.service.impl;

import com.inventory.dto.CartDTO;
import com.inventory.dto.OrderDTO;
import com.inventory.dto.OrderRequest;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.*;
import com.inventory.repository.CartRepository;
import com.inventory.repository.OrderRepository;
import com.inventory.repository.UserRepository;
import com.inventory.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private ModelMapper modelMapper;
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(UserRepository userRepository, CartRepository cartRepository, ModelMapper modelMapper, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDTO createOrder(OrderRequest request, String userName) {
        User user = userRepository.findByEmail(userName).orElseThrow(() -> new ResourceNotFoundException("User Not found"));
        int cartId = request.getCartId();
        String orderAddress = request.getOrderAddress();
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart not found"));
        Set<CartItem> items = cart.getItems();
        OrderEntity order = new OrderEntity();

        AtomicReference<Double> totalOrderPrice = new AtomicReference<>(0.0);
        Set<OrderItem> orderItems = items.stream().map((cartItem) -> {
            OrderItem orderItem = new OrderItem();


            //set cartItem into OrderItem

            //set product in orderitem
            orderItem.setProduct(cartItem.getProduct());

            //set productQTY in orderItem
            orderItem.setProductQuantity(cartItem.getQuantity());

            orderItem.setTotalProductPrice(cartItem.getTotalPrice());
            orderItem.setOrder(order);
            totalOrderPrice.set(totalOrderPrice.get() + orderItem.getTotalProductPrice());
            int productId = orderItem.getProduct().getProduct_id();
            return orderItem;


        }).collect(Collectors.toSet());

        order.setBillingAddress(orderAddress);
        order.setOrderDelivered(null);
        order.setOrderStatus("created");
        order.setPaymentStatus("Not Paid");
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setOrderAmt(totalOrderPrice.get());
        order.setOrderCreatedAt(new Date());
        OrderEntity save;
        if(order.getOrderAmt() > 0){
           save = orderRepository.save(order);
            cart.getItems().clear();
           cartRepository.save(cart);
        }else {
            throw new ResourceNotFoundException("Add cart first then place order");
        }

        return modelMapper.map(save, OrderDTO.class);
    }

    @Override
    public void CancelOrder(int orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order Not found"));
        orderRepository.delete(order);
    }

    @Override
    public OrderDTO findById(int orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("order didnt found"));
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }
}
