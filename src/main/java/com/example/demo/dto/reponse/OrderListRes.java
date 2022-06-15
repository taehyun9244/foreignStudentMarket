package com.example.demo.dto.reponse;

import com.example.demo.model.Address;
import com.example.demo.model.Order;
import lombok.Getter;


@Getter
public class OrderListRes {

    private Long orderId;
    private Long marketId;
    private String itemName;
    private int price;
    private String orderStatus;
    private String seller;
    private String buyer;
    private Address to_address;



    public OrderListRes(Order order) {
        this.orderId = order.getId();
        this.marketId = order.getMarketBoard().getId();
        this.itemName = order.getMarketBoard().getItemName();
        this.price = order.getMarketBoard().getPrice();
        this.orderStatus = order.getOrderStatus();
        this.seller = order.getMarketBoard().getUser().getUsername();
        this.buyer = order.getUser().getUsername();
        this.to_address = order.getUser().getAddress();

    }
}
