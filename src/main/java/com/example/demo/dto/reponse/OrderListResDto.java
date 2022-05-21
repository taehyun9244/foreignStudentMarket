package com.example.demo.dto.reponse;

import com.example.demo.model.Address;
import com.example.demo.model.Order;
import com.example.demo.util.DeliveryStatus;
import com.example.demo.util.OrderStatus;
import lombok.Getter;


@Getter
public class OrderListResDto {

    private Long marketId;
    private String itemName;
    private int price;
    private OrderStatus orderStatus;
    private Address to_address;

    public OrderListResDto(Order order) {
        this.marketId = order.getMarketBoard().getId();
        this.itemName = order.getMarketBoard().getItemName();
        this.price = order.getMarketBoard().getPrice();
        this.orderStatus = order.getOrderStatus();
        this.to_address = order.getUser().getAddress();
    }
}
