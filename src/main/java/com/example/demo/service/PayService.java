package com.example.demo.service;

import com.example.demo.dto.reponse.PayListRes;
import com.example.demo.dto.request.PayReq;
import com.example.demo.model.Order;
import com.example.demo.model.Pay;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PayRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.queryRepository.PayQueryRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.util.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PayService {

    private final PayRepository payRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PayQueryRepository payQueryRepository;


    //결제
    public void orderPay(UserDetailsImpl userDetails, PayReq payReq) {
        User user = userDetails.getUser();
        User payer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new RuntimeException("회원이 아닙니다")
        );
        Order orderItem = orderRepository.findById(payReq.getOrderId()).orElseThrow(
                () -> new RuntimeException("주문한 상품이 아닙니다")
        );

        boolean exist = payer.getOrders().contains(orderItem);
        log.info("orderItem.getStatus ={}", orderItem.getOrderStatus());
        log.info("exist = {}", exist);

        if (exist == true && orderItem.getOrderStatus().equals(OrderStatus.ORDER.getCode()))
            if (orderItem.getItemPrice() == payReq.getItemPrice()) {
                Pay pay = new Pay(payer, orderItem, payReq);
                payRepository.save(pay);
            }
    }

    //결제 상품 리스트 JPA
    @Transactional(readOnly = true)
    public List<PayListRes> payList(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        User payer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 회원입니다")
        );
        List<Pay> pays = payer.getPays();
        List<PayListRes> collect = pays.stream()
                .map(pay -> new PayListRes(pay))
                .collect(Collectors.toList());
        return collect;
    }

    //결제 상품 리스트 QueryDsl -> Dto
    public Page<PayListRes> payListV2(UserDetailsImpl userDetails, Pageable pageable) {
        User user = userDetails.getUser();
        Page<PayListRes> pays = payQueryRepository.findPayList(user.getUsername(), pageable);
        return pays;
    }
}


