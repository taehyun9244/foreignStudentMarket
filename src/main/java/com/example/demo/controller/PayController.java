package com.example.demo.controller;

import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.PayReq;
import com.example.demo.model.Order;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;

    //결제
    @PostMapping("/orderItems/v1/pays")
    public void orderPay(@AuthenticationPrincipal UserDetailsImpl userDetails,
                         @RequestBody PayReq payReq){
         payService.orderPay(userDetails, payReq);
    }

    @PostMapping("/orderItems/v2/pays")
    public void orderPayA(@AuthenticationPrincipal UserDetailsImpl userDetails,
                          @RequestBody Order order,
                          @RequestParam String payStatus){
        payService.orderPayA(userDetails, order, payStatus);
    }

    //결제 리스트
    @GetMapping("/api/v1/payItems")
    public Response payList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return payService.payList(userDetails);
    }
}
