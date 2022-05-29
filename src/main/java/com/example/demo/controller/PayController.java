package com.example.demo.controller;

import com.example.demo.dto.reponse.PayListRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.CancelPayReq;
import com.example.demo.dto.request.PayReq;
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

    //결제취소
    @PatchMapping("/orderItems/v1/pays")
    public void cancelPay( @AuthenticationPrincipal UserDetailsImpl userDetails,
                           @RequestBody CancelPayReq cancelPayReq){
         payService.cancelPay(userDetails, cancelPayReq);
    }

    //결제 리스트
    @GetMapping("/api/v1/payItems")
    public Response payList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return payService.payList(userDetails);
    }
}
