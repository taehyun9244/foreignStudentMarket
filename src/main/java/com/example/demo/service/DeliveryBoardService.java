package com.example.demo.service;


import com.example.demo.dto.reponse.DeliveryBoardDetailRes;
import com.example.demo.dto.reponse.DeliveryBoardSimRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.DeliveryBoardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.queryRepository.AllBoardQueryRepository;
import com.example.demo.repository.queryRepository.JpqlBoardQueryRepository;
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
@Transactional
@Slf4j
public class DeliveryBoardService {

    private final DeliveryBoardRepository deliveryBoardRepository;
    private final JpqlBoardQueryRepository queryRepository;
    private final UserRepository userRepository;
    private final AllBoardQueryRepository allBoardQueryRepository;


    //운송 게시글 전체 조회 jpql
    @Transactional(readOnly = true)
    public Response getBoardSim(int offset, int limit) {
        List<DeliveryBoard> deliveryBoards = queryRepository.findAllDeliBoard(offset, limit);
        List<DeliveryBoardSimRes> collect = deliveryBoards.stream()
                .map(deliveryBoard -> new DeliveryBoardSimRes(deliveryBoard))
                .collect(Collectors.toList());
        return new Response(collect);
    }


    //운송 게시글 전체 조회 QueryDsl -> dto
    @Transactional(readOnly = true)
    public Response getBoardSimV2(Pageable pageable) {
        Page<DeliveryBoardSimRes> deliveryBoards = allBoardQueryRepository.findByDeliveryBoardAllDto(pageable);
        for (DeliveryBoardSimRes deliveryBoard : deliveryBoards) {
            log.info("deliveryBoard = {}", deliveryBoard);
        }
        return new Response(deliveryBoards);
    }


    //운송 게시글 상세 조회 QueryDsl -> dto
    @Transactional(readOnly = true)
    public List<DeliveryBoardDetailRes> getBoardDetailV2(Long deliveryBoardsId) {
        List<DeliveryBoardDetailRes> findById = allBoardQueryRepository.findByDeliveryBoardIdDto(deliveryBoardsId);
        log.info("findById ={}", findById);
        return findById;
    }


    //운송 게시글 작성
    public void creatDeliveryBoard(DeliveryBoardPostReq postReqDto, UserDetailsImpl userDetails) {
            User user = userDetails.getUser();
            User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                    () -> new RuntimeException("회원가입을 해주세요 가입되지 않았습니다")
            );
            DeliveryBoard deliveryBoard = new DeliveryBoard(postReqDto,findUser);
            deliveryBoardRepository.save(deliveryBoard);
    }


    //운송 게시글 수정
    public void editDeliveryBoard(Long deliveryId, UserDetailsImpl userDetails, DeliveryBoardPostReq postReqDto){
        User user = userDetails.getUser();
        DeliveryBoard deliveryBoard = deliveryBoardRepository.findById(deliveryId)
                .orElseThrow( () ->new RuntimeException("존재하지 않는 게시글입니다")
        );
        if (user.getUsername().equals(deliveryBoard.getUser().getUsername())){
            deliveryBoard.editDeliveryBoard(postReqDto);
        }else {
            throw new RuntimeException("게시글 작성자가 아닙니다");
        }
    }


    //운송 게시글 삭제
    public void deleteDeliveryBoard(Long deliveryId, UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        DeliveryBoard deliveryBoard = deliveryBoardRepository.findById(deliveryId)
                .orElseThrow( () -> new RuntimeException("존재하지 않는 게시글입니다")
        );
        if (user.getUsername().equals(deliveryBoard.getUser().getUsername())){
            deliveryBoardRepository.delete(deliveryBoard);
        }else {
            throw new RuntimeException("게시글 작성자가 아닙니다");
        }
    }


}
