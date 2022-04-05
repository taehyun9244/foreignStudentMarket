package com.example.demo.controller;

import com.example.demo.dto.reponse.DeliveryBoardDetailResDto;
import com.example.demo.dto.reponse.DeliveryBoardSimResDto;
import com.example.demo.dto.request.DeliveryBoardPostReqDto;
import com.example.demo.service.DeliveryBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class DeliveryBoardController {

    private final DeliveryBoardService deliveryBoardService;

    public DeliveryBoardController(DeliveryBoardService deliveryBoardService) {
        this.deliveryBoardService = deliveryBoardService;
    }

    //운송 게시글 전체 조회
    @GetMapping("/deliveryBoards")
    public ResponseEntity<List<DeliveryBoardSimResDto>> getBoardSim() {
        return ResponseEntity.ok().body(deliveryBoardService.getBoardSim());
    }

    //운송 상세 게시글 조회
    @GetMapping("/deliveryBoards/{deliveryBoardId}")
    public ResponseEntity<DeliveryBoardDetailResDto> getBoardDetail(@PathVariable Long deliveryBoardId) {
        return ResponseEntity.ok().body(deliveryBoardService.getBoardDetail(deliveryBoardId));
    }


    //운송 게시글 작성
    @PostMapping("/deliveryBoards")
    public ResponseEntity<Void> creatDeliveryBoard(@RequestBody DeliveryBoardPostReqDto postReqDto){
        deliveryBoardService.creatDeliveryBoard(postReqDto);
        return ResponseEntity.created(URI.create("/deliveryBoard")).build();
    }

    //운송 게시글 수정
    @PatchMapping("/deliveryBoards/{deliveryBoardId}")
    public ResponseEntity<Void> editDeliveryBoard(@PathVariable Long deliveryBoardId, @RequestBody DeliveryBoardDetailResDto detailResDto){
        deliveryBoardService.editDeliveryBoard(deliveryBoardId, detailResDto);
        return ResponseEntity.ok().build();
    }

    //운송 게시글 삭제
    @DeleteMapping("/deliveryBoards/{deliveryBoardId}")
    public void deleteDeliveryBoard(@PathVariable Long deliveryBoardId){
        deliveryBoardService.deleteDeliveryBoard(deliveryBoardId);

    }

}