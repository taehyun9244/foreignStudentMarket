package com.example.demo.service;

import com.example.demo.dto.reponse.PayListRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.CancelPayReq;
import com.example.demo.dto.request.PayReq;
import com.example.demo.model.Delivery;
import com.example.demo.model.Order;
import com.example.demo.model.Pay;
import com.example.demo.model.User;
import com.example.demo.repository.DeliveryRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PayRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.util.DeliveryStatus;
import com.example.demo.util.OrderStatus;
import com.example.demo.util.PayStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final DeliveryRepository deliveryRepository;


    //결제
    public void orderPay(UserDetailsImpl userDetails, PayReq payReq) {
        User user = userDetails.getUser();
        User payer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new RuntimeException("회원이 아닙니다")
        );
        Order orderItem = orderRepository.findById(payReq.getOrderId()).orElseThrow(
                () -> new RuntimeException("주문한 상품이 아닙니다")
        );
        if (orderItem.getOrderStatus().equals(OrderStatus.CANCEL)) {
            throw new RuntimeException("주문이 취소된 상품입니다");
        }
        Pay pay = new Pay(payer, orderItem, payReq);
        payRepository.save(pay);
        }

    //결제 취소
    public void cancelPay(UserDetailsImpl userDetails, CancelPayReq cancelPayReq) {
        User user = userDetails.getUser();
        User payer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new RuntimeException("회원이 아닙니다")
        );
        log.info("payer ={}", payer);

        Order orderItem =  orderRepository.findById(cancelPayReq.getOrderId()).orElseThrow(
                ()-> new RuntimeException("주문한 상품이 아닙니다")
        );
        log.info("orderItem ={}", orderItem);

        Pay cancelPay = payRepository.findById(cancelPayReq.getPayId()).orElseThrow(
                ()-> new RuntimeException("결제한 상품이 아닙니다")
        );
        log.info("cancelPay ={}", cancelPay);

        Delivery delivery = deliveryRepository.findById(cancelPayReq.getDeliveryId()).orElseThrow(
                ()->new RuntimeException("배달을 찾을 수 없습니다")
        );
        log.info("delivery ={}", delivery);

        if (delivery.getDeliveryStatus().equals(DeliveryStatus.SHP)){
            throw new RuntimeException("배송중인 상품은 취소가 불가능합니다");
        }else cancelPay.setPayStatus(String.valueOf(PayStatus.REF));
    }

    //결제 상품 리스트
    @Transactional(readOnly = true)
    public Response payList(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        User payer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new RuntimeException("존재하지 않는 회원입니다")
        );
        List<Pay> pays = payer.getPays();
        List<PayListRes> collect = pays.stream()
                .map(pay -> new PayListRes(pay))
                .collect(Collectors.toList());
        return new Response(collect);
    }
}


