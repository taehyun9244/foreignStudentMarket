package com.example.demo.service;


import com.example.demo.dto.reponse.DeliveryBoardDetailRes;
import com.example.demo.dto.reponse.DeliveryBoardSimRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.DeliveryBoardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.queryRepository.BoardQueryRepository;
import com.example.demo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryBoardService {

    private final DeliveryBoardRepository deliveryBoardRepository;
    private final BoardQueryRepository queryRepository;
    private final UserRepository userRepository;


    //운송 게시글 전체 조회
    @Transactional(readOnly = true)
    public Response getBoardSim(int offset, int limit) {
        List<DeliveryBoard> deliveryBoards = queryRepository.findAllDeliBoard(offset, limit);
        List<DeliveryBoardSimRes> collect = deliveryBoards.stream()
                .map(deliveryBoard -> new DeliveryBoardSimRes(deliveryBoard))
                .collect(Collectors.toList());
        return new Response(collect);
    }

   //운송 게시글 상세 조회
    @Transactional(readOnly = true)
    public DeliveryBoardDetailRes getBoardDetail(Long deliveryBoardId) {
        DeliveryBoard deliveryBoard = deliveryBoardRepository.findById(deliveryBoardId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );
        return new DeliveryBoardDetailRes(deliveryBoard);
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
    public DeliveryBoard editDeliveryBoard(Long deliveryId, UserDetailsImpl userDetails, DeliveryBoardPostReq postReqDto){
        User user = userDetails.getUser();
        DeliveryBoard deliveryBoard = deliveryBoardRepository.findById(deliveryId)
                .orElseThrow( () ->new RuntimeException("존재하지 않는 게시글입니다")
        );
        if (user.getUsername().equals(deliveryBoard.getUser().getUsername())){
            return deliveryBoard.editDeliveryBoard(postReqDto);
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
