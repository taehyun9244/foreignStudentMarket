package com.example.demo.service;

import com.example.demo.dto.reponse.OrderListRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.OrderPostReq;
import com.example.demo.model.Delivery;
import com.example.demo.model.MarketBoard;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.MarketRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.util.DeliveryStatus;
import com.example.demo.util.OrderStatus;
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

    //주문생성
    @Transactional
    public void creatOrder(OrderPostReq postDto, UserDetailsImpl userDetails) {
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
    public void  cancelOrder(Long orderId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        User buyer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new RuntimeException("에러")
        );
        log.info("buyer ={}", buyer);
        Order cancelOrder = orderRepository.findById(orderId).orElseThrow(
                ()-> new RuntimeException("주문이 없습니다")
        );
        log.info("cancelOrder ={}", cancelOrder);
            orderRepository.delete(cancelOrder);
    }

    //주문상품 리스트 조회
    @Transactional(readOnly = true)
    public Response findOrderList(Long userId) {
         User user = userRepository.findById(userId).orElseThrow(
                 ()-> new RuntimeException("존재하지 않는 회원입니다")
         );
        List<Order> orders = user.getOrders();
        List<OrderListRes> collect = orders.stream()
                .map(order -> new OrderListRes(order))
                .collect(Collectors.toList());
        return new Response(collect);
    }

}
