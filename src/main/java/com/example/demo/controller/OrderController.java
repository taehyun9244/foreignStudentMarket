package com.example.demo.controller;

import com.example.demo.dto.reponse.OrderListRes;
import com.example.demo.dto.request.OrderReq;
import com.example.demo.model.Order;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문생성
    @PostMapping("/markets/v2/orders")
    public Order creatOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                            @RequestBody OrderReq postDto){
      return orderService.creatOrder(postDto, userDetails);
    }

    //주문취소
    @PostMapping("/markets/v1/orders/{orderId}")
    public void cancelOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                            @RequestParam String orderStatus,
                            @PathVariable Long orderId){
        orderService.cancelOrder(userDetails, orderStatus, orderId);
    }

    //주문상품 리스트 조회 JPA
    @GetMapping("/v1/orders/orderItems")
    public List<OrderListRes> orderList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return orderService.findOrderList(userDetails);
    }


    //주문상품 리스트 조회 QueryDsl
    @GetMapping("/v2/orders/orderItems")
    public Page<OrderListRes> orderListV2(@AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable){
        return orderService.findOrderListV2(pageable, userDetails);
    }



}
