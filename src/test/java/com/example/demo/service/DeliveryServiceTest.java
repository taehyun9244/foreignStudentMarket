package com.example.demo.service;

import com.example.demo.dto.request.DeliveryReq;
import com.example.demo.dto.request.MarketPostReq;
import com.example.demo.dto.request.OrderReq;
import com.example.demo.dto.request.PayReq;
import com.example.demo.model.*;
import com.example.demo.repository.DeliveryRepository;
import com.example.demo.repository.PayRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Slf4j
class DeliveryServiceTest {
    @InjectMocks
    private DeliveryService deliveryService;
    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private PayRepository payRepository;
    @Mock
    private UserRepository userRepository;

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
    private Delivery namDelivery;

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

        namDelivery =
                new Delivery(namPay1, new DeliveryReq(namPay1.getId(), "macBook", "OA"),namRegister.getUser());
    }

    @Test
    @DisplayName("배달 시작")
    void startDelivery() {
        //given
        DeliveryReq deliveryReq = new DeliveryReq(namPay1.getId(), "macBook", "DC");
        given(userRepository.findByUsername(namRegister.getUsername())).willReturn(Optional.ofNullable(namRegister.getUser()));
        given(payRepository.findById(namPay1.getId())).willReturn(Optional.ofNullable(namPay1));

        //when
        deliveryService.startDelivery(deliveryReq, namRegister);

        //then
        verify(deliveryRepository, times(0)).save(namDelivery);
    }

    @Test
    @DisplayName("결제정보 찾을 수 없어 배달 실패")
    void failDeliveryNoPay() {
        //when && then
        assertThatThrownBy(()-> deliveryService.startDelivery(null, namRegister))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("회원이 아니여서 배달 실패")
    void failDeliveryNoUser() {
        //when && then
        DeliveryReq deliveryReq = new DeliveryReq(namPay1.getId(), "macBook", "DC");
        assertThatThrownBy(()-> deliveryService.startDelivery(deliveryReq, null))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("결제상태 COMP 아니여서 실패")
    void failDeliveryPayNoComp() {
        //given
        Pay noPay =
                new Pay(namRegister.getUser(), namOrder1,
                        new PayReq(namOrder1.getId(),
                                null,
                                "macBook",
                                1000));


        DeliveryReq deliveryReq = new DeliveryReq(namPay1.getId(), "macBook", "OA");

        //when && then
        assertThatThrownBy(()-> deliveryService.startDelivery(deliveryReq, namRegister))
                .isInstanceOf(Exception.class);
    }
}