package com.example.demo.service;

import com.example.demo.dto.reponse.OrderListRes;
import com.example.demo.dto.reponse.PayListRes;
import com.example.demo.dto.request.MarketPostReq;
import com.example.demo.dto.request.OrderReq;
import com.example.demo.dto.request.PayReq;
import com.example.demo.model.*;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PayRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.queryRepository.PayQueryRepository;
import com.example.demo.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Slf4j
class PayServiceTest {

    @InjectMocks
    private PayService payService;
    @Mock
    private PayRepository payRepository;
    @Mock
    private PayQueryRepository payQueryRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;

    private UserDetailsImpl userDetailsNull;
    private UserDetailsImpl namRegister;
    private UserDetailsImpl ayaRegister;
    private MarketBoard namMarketBoard;
    private MarketBoard ayaMaretBoard1;
    private MarketBoard ayaMaretBoard2;
    private MarketPostReq marketPostReq;
    private MarketPostReq marketPostReq1;
    private MarketPostReq marketPostReq2;
    private Order namOrder1;
    private Order namOrder2;
    private Pay namPay1;
    private Pay namPay2;
    private PayReq payReq1;
    private PayReq payReq2;

    @BeforeEach
    public void setUp() {

        //User: null
        userDetailsNull = null;

        //User: Nam
        Address namAddress = new Address("Seoul", "Seocho", "132");
        User nam = new User("nam", "1234", "20220404", "123@naver.com",
                "010-1111-1111", namAddress);
        namRegister = new UserDetailsImpl(nam);

        //MarketBoard: namBoard
        marketPostReq = new MarketPostReq("맥북16인치", "신형", "ET", "서울서초", 1000);
        namMarketBoard = new MarketBoard(marketPostReq, namRegister.getUser());

        //User: aya
        Address ayaAddress = new Address("Tokyo", "Sibuya", "155");
        User aya = new User("aya", "1234", "20220528", "999@naver.com",
                "080-9999-9999", ayaAddress);
        ayaRegister = new UserDetailsImpl(aya);

        //MarketBoard: ayaBoard
        marketPostReq1 = new MarketPostReq("아이패드12.9", "중고", "ET", "도쿄시부야", 1000);
        marketPostReq2 = new MarketPostReq("아이맥", "신형", "ET", "도쿄시부야", 4000);
        ayaMaretBoard1 = new MarketBoard(marketPostReq1, ayaRegister.getUser());
        ayaMaretBoard2 = new MarketBoard(marketPostReq2, ayaRegister.getUser());

        namOrder1 = new Order(namRegister.getUser(), ayaMaretBoard1, new OrderReq(ayaMaretBoard1.getId(), "ORDER"));
        namOrder2 = new Order(namRegister.getUser(), ayaMaretBoard2, new OrderReq(ayaMaretBoard2.getId(), "ORDER"));

        payReq1 = new PayReq(namOrder1.getId(), "PAY", "아이패드12.9", 1000);
        payReq2 = new PayReq(namOrder1.getId(), "PAY", "아이맥", 4000);
        namPay1 = new Pay(namRegister.getUser(), namOrder1, payReq1);
        namPay2 = new Pay(namRegister.getUser(), namOrder1, payReq2);
    }

    @Test
    @DisplayName("결제 성공")
    void orderPay() {
        //given
        given(userRepository.findByUsername(namRegister.getUsername())).willReturn(Optional.ofNullable(namRegister.getUser()));
        given(orderRepository.findById(namOrder1.getId())).willReturn(Optional.ofNullable(namOrder1));

        //when
        payService.orderPay(namRegister, payReq1);

        //then
        verify(payRepository, times(0)).save(namPay1);
    }

    @Test
    @DisplayName("결제 리스트 JPA")
    void payListJpa() {
        //given
        PayListRes payListRes1 = new PayListRes(namPay1);
        PayListRes payListRes2 = new PayListRes(namPay2);

        List<PayListRes> payListRes = new ArrayList<>();
        payListRes.add(payListRes1);
        payListRes.add(payListRes2);

        given(userRepository.findByUsername(namRegister.getUsername())).willReturn(Optional.ofNullable(namRegister.getUser()));

        //when
        List<PayListRes> namPayList = payService.payList(namRegister);

        //then
        assertThat(namPayList.contains(namPay1));
        assertThat(namPayList.contains(namPay2));

    }

    @Test
    @DisplayName("결제 리스트 querydsl")
    void payListQuerydsl() {
        //given
        PayListRes payListRes1 = new PayListRes(namPay1);
        PayListRes payListRes2 = new PayListRes(namPay2);
        List<PayListRes> payListResList = new ArrayList<>();
        payListResList.add(payListRes1);
        payListResList.add(payListRes2);

        Page<PayListRes> payListResPage = new PageImpl<>(payListResList);
        given(payQueryRepository.findPayList(namRegister.getUsername(), Pageable.ofSize(10))).willReturn(payListResPage);

        //when
        Page<PayListRes> namPayList = payService.payListV2(namRegister, Pageable.ofSize(10));

        //then
        assertThat(namPayList.getTotalElements()).isEqualTo(2);
        assertThat(namPayList.toList().contains(namPay1));
        assertThat(namPayList.toList().contains(namPay2));

    }

    @Test
    @DisplayName("비회원 결제 불가")
    void failPayNoUser() {
        //when && then
        assertThatThrownBy(()-> payService.orderPay(null, payReq1))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("주문한 상품이 아닐 시 결제 불가")
    void failPayNoOrder() {
        //when && then
        PayReq payReq = new PayReq(null, "ORDER", "macBook", 1000);
        assertThatThrownBy(()-> payService.orderPay(namRegister, payReq))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("상품은 존재, OrderStatus.Order 아닐경우 결제 실패")
    void failPayOrderStatusNotOrder() {
        //given
        Order cancelOrder = new Order(namRegister.getUser(), namMarketBoard, new OrderReq(namMarketBoard.getId(), "CANCEL"));
        PayReq payReq = new PayReq(cancelOrder.getId(), cancelOrder.getOrderStatus(), "macBook", 1000);

        //when && then
        assertThatThrownBy(()-> payService.orderPay(namRegister, payReq))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("상품가격과 결제가격이 다른 경우 실패")
    void failPayNotSamePrice() {
        //given
        Order order = new Order(namRegister.getUser(), namMarketBoard, new OrderReq(namMarketBoard.getId(), "ORDER"));
        PayReq payReq = new PayReq(order.getId(), order.getOrderStatus(), "macBook", 500);

        //when && then
        assertThatThrownBy(()-> payService.orderPay(namRegister, payReq))
                .isInstanceOf(Exception.class);
        assertThat(order.getItemPrice()).isNotEqualTo(payReq.getItemPrice());
    }

    @Test
    @DisplayName("회원이 아닐시 결제 리스트 조회 불가")
    void failListNoUser() {
        //when && then
        assertThatThrownBy(()-> payService.payList(null))
                .isInstanceOf(Exception.class);
    }



}