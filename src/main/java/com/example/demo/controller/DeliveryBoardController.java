package com.example.demo.controller;

import com.example.demo.dto.reponse.DeliveryBoardDetailRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.DeliveryBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DeliveryBoardController {

    private final DeliveryBoardService deliveryBoardService;

    //운송 게시글 전체 조회 Jpql
    @GetMapping("/deliveryBoards")
    public Response getBoardSim(@RequestParam(value ="offset", defaultValue = "0") int offset,
                                @RequestParam(value = "limit", defaultValue = "100")int limit) {
        return deliveryBoardService.getBoardSim(offset, limit);
    }

    //운송 게시글 전체 조회 querydsl-> dto 조회
    @GetMapping("/deliveryBoardsV2")
    public Response getBoardSimV2(Pageable pageable) {
        return deliveryBoardService.getBoardSimV2(pageable);
    }

    //운송 게시글 상세 조회 dto
    @GetMapping("/deliveryBoardsV2/{deliveryBoardsId}")
    public List<DeliveryBoardDetailRes> getBoardDetailV2(@PathVariable Long deliveryBoardsId){
        return deliveryBoardService.getBoardDetailV2(deliveryBoardsId);
    }

    //운송 게시글 작성
    @PostMapping("/deliveryBoards")
    public void creatDeliveryBoard(@RequestBody DeliveryBoardPostReq postReqDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        deliveryBoardService.creatDeliveryBoard(postReqDto, userDetails);
    }

    //운송 게시글 수정
    @PutMapping("/deliveryBoards/{deliveryBoardsId}")
    public DeliveryBoard editDeliveryBoard(@PathVariable Long deliveryBoardsId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestBody DeliveryBoardPostReq postReqDto){
        return deliveryBoardService.editDeliveryBoard(deliveryBoardsId, userDetails, postReqDto);
    }


    //운송 게시글 삭제
    @DeleteMapping("/deliveryBoards/{deliveryBoardsId}")
    public void deleteDeliveryBoard(@PathVariable Long deliveryBoardsId,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails){
        deliveryBoardService.deleteDeliveryBoard(deliveryBoardsId, userDetails);
    }
}