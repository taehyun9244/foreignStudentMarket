package com.example.demo.service;

import com.example.demo.dto.request.PayReq;
import com.example.demo.model.Delivery;
import com.example.demo.model.Order;
import com.example.demo.model.Pay;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PayRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.util.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PayService {

    private final PayRepository payRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;


    //결제
    public Pay orderPay(Long orderId, UserDetailsImpl userDetails, PayReq payReq) {
        User user = userDetails.getUser();
        User payer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new RuntimeException("")
        );
        Order orderItem = orderRepository.findById(orderId).orElseThrow(
                ()-> new RuntimeException("")
        );
        DeliveryStatus deliveryStatus = DeliveryStatus.DC;
        Pay pay = new Pay(payer, orderItem, payReq);
        Delivery delivery = new Delivery(payer, orderItem,deliveryStatus);
        return payRepository.save(pay);
    }
}
