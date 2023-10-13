package com.mymy.ecommerce.dto;

import java.util.Set;

import com.mymy.ecommerce.entity.Address;
import com.mymy.ecommerce.entity.Customer;
import com.mymy.ecommerce.entity.Order;
import com.mymy.ecommerce.entity.OrderItem;

import lombok.Data;

@Data
public class Purchase {
	
	private Customer customer;
	
	private Address shippingAddress;
	
	private Address billingAddress;
	
	private Order order;
	
	private Set<OrderItem> orderItems;

}
