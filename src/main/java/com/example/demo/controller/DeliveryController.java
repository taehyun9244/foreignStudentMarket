package com.example.demo.controller;

import com.example.demo.dto.request.DeliveryReq;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    //delivery 시작
    @PostMapping("/payItems/deliveries")
    public void startDelivery(@RequestBody DeliveryReq deliveryReq,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        deliveryService.startDelivery(deliveryReq, userDetails);
    }
}
