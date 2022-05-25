package com.example.demo.controller;

import com.example.demo.dto.request.PayReq;
import com.example.demo.model.Pay;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;

    @PostMapping("/api/v1/{orderId}/pay")
    public Pay orderPay(@PathVariable Long orderId,
                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                        @RequestBody PayReq payReq){
        return payService.orderPay(orderId, userDetails, payReq);
    }


}
