package com.example.demo.controller;

import com.example.demo.dto.reponse.MarketDetailRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.MarketPostReq;
import com.example.demo.model.MarketBoard;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MarketController {

    private final MarketService marketService;

    //전체 중고게시글 조회
//    @GetMapping("/markets")
//     public Response getAllListMarket(@RequestParam(value ="offset", defaultValue = "0") int offset,
//                                      @RequestParam(value = "limit", defaultValue = "100") int limit){
//       return marketService.getAllListMarket(offset, limit);
//    }

    //전체 중고게시글 조회 QueryDsl -> Dto
    @GetMapping("/markets")
     public Response getAllListMarketV2(Pageable pageable){
       return marketService.getAllListMarketV2(pageable);
    }


    //싱세 중고게시글 조회 QueryDsl -> Dto
    @GetMapping("/markets/{marketId}")
    public List<MarketDetailRes> getDetailMarketV2(@PathVariable Long marketId) {
        return marketService.getDetailMarketV2(marketId);
    }

    //중고게시글 작성
    @PostMapping("/markets")
    public void creatMarketBoard(@RequestBody MarketPostReq postDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("itemName = {}", postDto.getItemName());
        log.info("username={}", userDetails.getUsername());
        marketService.creatMarketBoard(postDto, userDetails);
    }

    //게시글 이미지 저장
    @PostMapping("/markets/{marketId}")
    public void creatMarketImages(@PathVariable Long marketId,
                                  @RequestPart("file") List<MultipartFile> multipartFiles,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        log.info("file name = {}", multipartFiles.size());
        marketService.creatMarketImages(marketId, multipartFiles, userDetails);
    }

    //중고게시글 수정
    @PutMapping("/markets/{marketId}")
    public MarketBoard editMarketBoard(@PathVariable Long marketId,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @RequestPart MarketPostReq postReq,
                                       @RequestPart("file") List<MultipartFile> multipartFiles) throws IOException {
        return marketService.editMarketBoard(marketId, userDetails, postReq, multipartFiles);
    }

    //중고게시글 삭제
    @DeleteMapping("/markets/{marketId}")
    public void deleteMarketBoard(@PathVariable Long marketId,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        marketService.deleteMarketBoard(marketId, userDetails);
    }

}
