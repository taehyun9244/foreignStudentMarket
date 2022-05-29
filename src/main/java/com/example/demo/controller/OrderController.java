package com.example.demo.controller;

import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.OrderReq;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문생성
    @PostMapping("/markets/v1/orders")
    public void creatOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @RequestBody OrderReq postDto){
        orderService.creatOrder(postDto, userDetails);
    }

    //주문취소
    @PostMapping("/markets/v1/orders/{orderId}")
    public void cancelOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                            @RequestParam String orderStatus,
                            @PathVariable Long orderId){
        orderService.cancelOrder(userDetails, orderStatus, orderId);
    }

    //주문상품 리스트 조회
    @GetMapping("/v1/orders/orderItems")
    public Response orderList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return orderService.findOrderList(userDetails);
    }



}
