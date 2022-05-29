package com.example.demo.service;

import com.example.demo.dto.reponse.OrderListRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.CancelOrderReq;
import com.example.demo.dto.request.OrderReq;
import com.example.demo.model.MarketBoard;
import com.example.demo.model.Order;
import com.example.demo.model.Pay;
import com.example.demo.model.User;
import com.example.demo.repository.MarketRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PayRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.util.PayStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final UserRepository userRepository;
    private final MarketRepository marketRepository;
    private final OrderRepository orderRepository;
    private final PayRepository payRepository;

    //주문생성
    @Transactional
    public void creatOrder(OrderReq postDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        User buyer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new RuntimeException("회원가입 후 주문 할 수 있습니다")
        );
        MarketBoard orderItem = marketRepository.findById(postDto.getMarketId()).orElseThrow(
                ()-> new RuntimeException("주문상품이 존재하지 않습니다")
        );
        //주문 생성
        Order order = new Order(buyer, orderItem, postDto);
        orderRepository.save(order);
    }

    //주문취소
    @Transactional
    public void  cancelOrder(UserDetailsImpl userDetails, CancelOrderReq cancelOrderReq) {
        User user = userDetails.getUser();
        User buyer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new RuntimeException("에러")
        );
        log.info("buyer ={}", buyer);

        Order cancelOrder = orderRepository.findById(cancelOrderReq.getOrderId()).orElseThrow(
                ()-> new RuntimeException("주문이 없습니다")
        );
        log.info("cancelOrder = {}", cancelOrder);

        Pay pay = payRepository.findById(cancelOrderReq.getPayId()).orElseThrow(
                ()-> new RuntimeException("결제상품이 아닙니다")
        );
        log.info("pay = {}", pay);

        if (pay.getPayStatus().equals(PayStatus.COMP)){
          throw new RuntimeException("결제완료된 상품은 주문 취소 할 수 없습니다");
        }else cancelOrder.setOrderStatus(cancelOrder.getOrderStatus());
    }

    //주문상품 리스트 조회
    @Transactional(readOnly = true)
    public Response findOrderList(UserDetailsImpl userDetails) {
         User user = userDetails.getUser();
         User buyer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                 ()-> new RuntimeException("존재하지 않는 회원입니다")
         );
        List<Order> orders = buyer.getOrders();
        List<OrderListRes> collect = orders.stream()
                .map(order -> new OrderListRes(order))
                .collect(Collectors.toList());
        return new Response(collect);
    }

}
