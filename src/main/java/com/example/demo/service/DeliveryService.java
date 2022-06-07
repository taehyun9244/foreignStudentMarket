package com.example.demo.service;

import com.example.demo.dto.request.DeliveryReq;
import com.example.demo.model.Delivery;
import com.example.demo.model.Pay;
import com.example.demo.model.User;
import com.example.demo.repository.DeliveryRepository;
import com.example.demo.repository.PayRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.util.PayStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final PayRepository payRepository;
    private final UserRepository userRepository;

    //배달 시작
    @Transactional
    public void startDelivery(DeliveryReq deliveryReq, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        User payer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 회원입니다")
        );

        Pay pay = payRepository.findById(deliveryReq.getPayId()).orElseThrow(
                () -> new RuntimeException("결제 정보를 찾을 수 없습니다")
        );

        boolean exist = payer.getPays().contains(pay);
        log.info("exist ={}", exist);
        log.info("pay.getStatus ={}", pay.getPayStatus());

        if ( exist == true && pay.getPayStatus().equals(PayStatus.COMP.getCode())){
            Delivery delivery = new Delivery(pay, deliveryReq, payer);
            deliveryRepository.save(delivery);
        }
    }
}
