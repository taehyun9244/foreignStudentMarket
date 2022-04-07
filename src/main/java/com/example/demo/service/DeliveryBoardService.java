package com.example.demo.service;

import com.example.demo.dto.reponse.DeliveryBoardDetailResDto;
import com.example.demo.dto.reponse.DeliveryBoardSimResDto;
import com.example.demo.dto.request.DeliveryBoardPostReqDto;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.DeliveryBoardRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryBoardService {

    private final DeliveryBoardRepository deliveryBoardRepository;
    private final UserRepository userRepository;

    @Autowired
    public DeliveryBoardService(DeliveryBoardRepository deliveryBoardRepository, UserRepository userRepository) {
        this.deliveryBoardRepository = deliveryBoardRepository;
        this.userRepository = userRepository;
    }

    //운송 게시글 전체조회
    public List<DeliveryBoardSimResDto> getBoardSim(){
        List<DeliveryBoard> deliveryBoardList = deliveryBoardRepository.findAllByOrderByCreatedAtDesc();
        return DeliveryBoardSimResDto.list(deliveryBoardList);
    }

    //운송 게시글 상세조회
    public DeliveryBoardDetailResDto getBoardDetail(Long deliveryBoardId) {
        DeliveryBoard deliveryBoard = deliveryBoardRepository.findById(deliveryBoardId).orElseThrow(
                ()-> new IllegalArgumentException("Error")
        );
        return DeliveryBoardDetailResDto.of(deliveryBoard);
    }

    //운송 게시글 작성
    public void creatDeliveryBoard(DeliveryBoardPostReqDto postReqDto) {
        User findUsername = userRepository.findByUsername(postReqDto.getUsername()).orElseThrow(
                ()-> new IllegalArgumentException("can not found user")
        );
        deliveryBoardRepository.save(postReqDto.toEntity(findUsername));
    }

    //운송 게시글 수정
    public Long editDeliveryBoard(Long deliveryBoardId, DeliveryBoardDetailResDto detailResDto ) {
        DeliveryBoard deliveryBoard = deliveryBoardRepository.findById(deliveryBoardId).orElseThrow(
                ()-> new IllegalArgumentException("can not find user")
        );
        deliveryBoard.editDeliveryBoard(detailResDto);
        return deliveryBoardId;
    }

    //운송 게시글 삭제
    public void deleteDeliveryBoard(Long deliveryBoardId){
        DeliveryBoard deliveryBoard = deliveryBoardRepository.findById(deliveryBoardId).orElseThrow(
                ()-> new RuntimeException("can not found user")
        );
        deliveryBoardRepository.delete(deliveryBoard);
    }

}
