package com.inventory.controller;

import com.inventory.dto.ApiResponse;
import com.inventory.dto.OrderDTO;
import com.inventory.dto.OrderRequest;
import com.inventory.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequest orderRequest, Principal p){
        String username = p.getName();
       OrderDTO order = orderService.createOrder(orderRequest,username);
       return new ResponseEntity<OrderDTO>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable("orderId") int orderId) {
        orderService.CancelOrder(orderId);
        ApiResponse response = new ApiResponse("Order is cancelled", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> findById(@PathVariable("orderId") int orderId){
        OrderDTO byId = orderService.findById(orderId);
        return new ResponseEntity<OrderDTO>(byId,HttpStatus.ACCEPTED);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
