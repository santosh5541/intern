package com.inventory.service;

import com.inventory.dto.OrderDTO;
import com.inventory.dto.OrderRequest;

import java.util.List;

public interface OrderService {
    public OrderDTO createOrder(OrderRequest request, String userName);
    public void CancelOrder(int orderId);

    public OrderDTO findById(int orderId);

    public List<OrderDTO> getAllOrders();
}
