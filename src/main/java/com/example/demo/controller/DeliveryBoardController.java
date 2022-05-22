package com.example.demo.controller;

import com.example.demo.dto.reponse.DeliveryBoardDetailRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.DeliveryBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DeliveryBoardController {

    private final DeliveryBoardService deliveryBoardService;

    //운송 게시글 전체 조회
    @GetMapping("/deliveryBoards")
    public Response getBoardSim(@RequestParam(value ="offset", defaultValue = "0") int offset,
                                @RequestParam(value = "limit", defaultValue = "100")int limit) {
        return deliveryBoardService.getBoardSim(offset, limit);
    }

    //운송 게시글 상세 조회
    @GetMapping("/deliveryBoards/{deliveryBoardsId}")
    public DeliveryBoardDetailRes getBoardDetail(@PathVariable Long deliveryBoardsId){
        return deliveryBoardService.getBoardDetail(deliveryBoardsId);
    }

    //운송 게시글 작성
    @PostMapping("/deliveryBoards")
    public void creatDeliveryBoard(@RequestBody DeliveryBoardPostReq postReqDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        deliveryBoardService.creatDeliveryBoard(postReqDto, userDetails);
    }

    //운송 게시글 수정
    @PatchMapping("/deliveryBoards/{deliveryBoardsId}")
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