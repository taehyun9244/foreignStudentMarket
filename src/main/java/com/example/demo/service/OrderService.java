package com.example.demo.service;

import com.example.demo.dto.reponse.OrderListRes;
import com.example.demo.dto.request.OrderReq;
import com.example.demo.model.MarketBoard;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.MarketRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.queryRepository.OrderQueryRepository;
import com.example.demo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final OrderQueryRepository orderQueryRepository;

    //주문생성
    @Transactional
    public Order creatOrder(OrderReq postDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        User buyer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new RuntimeException("회원가입 후 주문 할 수 있습니다")
        );
        MarketBoard orderItem = marketRepository.findById(postDto.getMarketId()).orElseThrow(
                ()-> new RuntimeException("주문상품이 존재하지 않습니다")
        );
        if (orderItem.getUser() != buyer) {
            Order order = new Order(buyer, orderItem, postDto);
            return orderRepository.save(order);
        }else throw new RuntimeException("자신의 게시물은 주문 할 수 없습니다");
    }

    //주문취소
    @Transactional
    public void cancelOrder(UserDetailsImpl userDetails, String orderStatus, Long orderId) {
        User user = userDetails.getUser();
        User buyer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new RuntimeException("에러")
        );
        log.info("buyer ={}", buyer);

        Order cancelOrder = orderRepository.findById(orderId).orElseThrow(
                ()-> new RuntimeException("주문이 없습니다")
        );
        log.info("cancelOrder = {}", cancelOrder);

        cancelOrder.setOrderStatus(orderStatus);
    }

    //주문상품 리스트 조회
    @Transactional(readOnly = true)
    public List<OrderListRes> findOrderList(UserDetailsImpl userDetails) {
         User user = userDetails.getUser();
         User buyer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                 ()-> new RuntimeException("존재하지 않는 회원입니다")
         );
        List<Order> orders = buyer.getOrders();
        List<OrderListRes> collect = orders.stream()
                .map(order -> new OrderListRes(order))
                .collect(Collectors.toList());
        return collect;
    }

    //주문상품 리스트 조회 QueryDsl -> Dto
    @Transactional(readOnly = true)
    public Page<OrderListRes> findOrderListV2(Pageable pageable, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Page<OrderListRes> orders = orderQueryRepository.findOrderList(pageable, user.getUsername());
        return orders;
    }
}
