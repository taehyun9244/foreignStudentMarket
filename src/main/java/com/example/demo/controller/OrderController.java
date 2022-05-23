package com.example.demo.controller;

import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.OrderPostReq;
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
                           @RequestBody OrderPostReq postDto){
        orderService.creatOrder(postDto, userDetails);
    }

    //주문취소
    @DeleteMapping("/markets/v1/orders/{orderId}")
    public void cancelOrder(@PathVariable Long orderId,
                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        orderService.cancelOrder(orderId, userDetails);
    }

    //주문상품 리스트 조회
    @GetMapping("/{userId}/v1/orderItems")
    public Response orderList(@PathVariable Long userId){
        return orderService.findOrderList(userId);
    }



}
