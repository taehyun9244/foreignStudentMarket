package com.example.demo.controller;

import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.PayReq;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;

    //결제
    @PostMapping("/orderItems/v2/pays")
    public void orderPay(@AuthenticationPrincipal UserDetailsImpl userDetails,
                         @RequestBody PayReq payReq){
         payService.orderPay(userDetails, payReq);
    }

    //결제 리스트 JPA
    @GetMapping("/api/v1/payItems")
    public Response payList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return payService.payList(userDetails);
    }

    //결제 리스트 QueryDsl
    @GetMapping("/api/v2/payItems")
    public Response payListV2(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              Pageable pageable){
        return payService.payListV2(userDetails, pageable);
    }
}
