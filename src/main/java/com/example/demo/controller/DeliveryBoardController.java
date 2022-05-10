package com.example.demo.controller;

import com.example.demo.dto.reponse.DeliveryBoardDetailResDto;
import com.example.demo.dto.reponse.DeliveryBoardSimResDto;
import com.example.demo.dto.reponse.ResultList;
import com.example.demo.dto.request.DeliveryBoardPostReqDto;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.DeliveryBoardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeliveryBoardController {

    private final DeliveryBoardService deliveryBoardService;

    public DeliveryBoardController(DeliveryBoardService deliveryBoardService) {
        this.deliveryBoardService = deliveryBoardService;
    }

    //운송 게시글 전체 조회
    @GetMapping("/deliveryBoards")
    public ResultList getBoardSim(@RequestParam(value ="offset", defaultValue = "0") int offset,
                                  @RequestParam(value = "limit", defaultValue = "100")int limit) {
        return deliveryBoardService.getBoardSim(offset, limit);
    }

    //운송 게시글 상세 조회
    @GetMapping("/deliveryBoards/{deliveryBoardsId}")
    public DeliveryBoardDetailResDto getBoardDetail(@PathVariable Long deliveryBoardsId){
        return deliveryBoardService.getBoardDetail(deliveryBoardsId);
    }

    //운송 게시글 작성
    @PostMapping("/deliveryBoards")
    public void creatDeliveryBoard(@RequestBody DeliveryBoardPostReqDto postReqDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        deliveryBoardService.creatDeliveryBoard(postReqDto, userDetails);
    }

    //운송 게시글 수정
    @PutMapping("/deliveryBoards/{deliveryBoardsId}")
    public void editDeliveryBoard(@PathVariable Long deliveryBoardsId,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @RequestBody DeliveryBoardPostReqDto postReqDto){
        deliveryBoardService.editDeliveryBoard(deliveryBoardsId, userDetails, postReqDto);
    }


    //운송 게시글 삭제
    @DeleteMapping("/deliveryBoards/{deliveryBoardsId}")
    public void deleteDeliveryBoard(@PathVariable Long deliveryBoardsId,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails){
        deliveryBoardService.deleteDeliveryBoard(deliveryBoardsId, userDetails);
    }
}