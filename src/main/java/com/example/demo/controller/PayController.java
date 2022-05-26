package com.example.demo.controller;

import com.example.demo.dto.request.PayReq;
import com.example.demo.model.Pay;
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
    @PostMapping("/api/v1/{orderId}/pay")
    public Pay orderPay(@PathVariable Long orderId,
                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                        @RequestBody PayReq payReq){
        return payService.orderPay(orderId, userDetails, payReq);
    }

    //결제취소
//    @PatchMapping("/api/v1/{payId}")
//    public Pay cancelPay(@PathVariable Long payId,
//                         @AuthenticationPrincipal UserDetailsImpl userDetails,
//                         ){
//
//    }


}
