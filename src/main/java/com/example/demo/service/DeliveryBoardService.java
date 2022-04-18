package com.example.demo.service;


import com.example.demo.dto.reponse.DeliveryBoardDetailResDto;
import com.example.demo.dto.reponse.DeliveryBoardSimResDto;
import com.example.demo.dto.request.DeliveryBoardPostReqDto;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.DeliveryBoardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    //운송 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<DeliveryBoardSimResDto> getBoardSim() {
        List<DeliveryBoard> deliveryBoards = deliveryBoardRepository.findAllByOrderByCreatedAtDesc();
        List<DeliveryBoardSimResDto> simResDtos = new ArrayList<>();
        for (DeliveryBoard deliveryBoard : deliveryBoards)
            simResDtos.add(new DeliveryBoardSimResDto(deliveryBoard));
            return simResDtos;
    }

    //운송 게시글 상세 조회
    @Transactional(readOnly = true)
    public DeliveryBoardDetailResDto getBoardDetail(Long deliveryBoardId) {
        DeliveryBoard deliveryBoard = deliveryBoardRepository.findById(deliveryBoardId).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );
        return new DeliveryBoardDetailResDto(deliveryBoard);
    }

    //운송 게시글 작성
    @Transactional
    public void creatDeliveryBoard(DeliveryBoardPostReqDto postReqDto, UserDetailsImpl userDetails) {
            User user = userDetails.getUser();
            User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                    ()-> new RuntimeException("회원가입을 해주세요 가입되지 않았습니다")
            );
            DeliveryBoard deliveryBoard = new DeliveryBoard(postReqDto,findUser);
            deliveryBoardRepository.save(deliveryBoard);
    }

    //운송 게시글 수정
    @Transactional
    public void editDeliveryBoard(Long deliveryId, UserDetailsImpl userDetails, DeliveryBoardPostReqDto postReqDto){
        User user = userDetails.getUser();
        DeliveryBoard deliveryBoard = deliveryBoardRepository.findById(deliveryId)
                .orElseThrow( ()->new RuntimeException("존재하지 않는 게시글입니다")
        );
        if (user.getUsername().equals(deliveryBoard.getUser().getUsername())){
            deliveryBoard.editDeliveryBoard(postReqDto);
        }else {
            throw new RuntimeException("게시글 작성자가 아닙니다");
        }
    }

    //운송 게시글 삭제
    @Transactional
    public void deleteDeliveryBoard(Long deliveryId, UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        DeliveryBoard deliveryBoard = deliveryBoardRepository.findById(deliveryId)
                .orElseThrow( ()-> new RuntimeException("존재하지 않는 게시글입니다")
        );
        if (user.getUsername().equals(deliveryBoard.getUser().getUsername())){
            deliveryBoardRepository.delete(deliveryBoard);
        }else {
            throw new RuntimeException("게시글 작성자가 아닙니다");
        }
    }


}
