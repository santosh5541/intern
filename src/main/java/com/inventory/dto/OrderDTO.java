package com.inventory.dto;

import com.inventory.model.OrderItem;
import com.inventory.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private int orderId;
    private String orderStatus;
    private Date orderDelivered;
    private double orderAmt;
    private String billingAddress;
    private String paymentStatus;
    private Date orderCreatedAt;
    private UserDTO user;
   private Set<OrderItemDTO> orderItems = new HashSet<>();

}
