package com.example.demo.controller;

import com.example.demo.dto.reponse.MarketDetailRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.MarketPostReq;
import com.example.demo.model.MarketBoard;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MarketController {

    private final MarketService marketService;

    //전체 중고게시글 조회
    @GetMapping("/markets")
     public Response getAllListMarket(@RequestParam(value ="offset", defaultValue = "0") int offset,
                                      @RequestParam(value = "limit", defaultValue = "100") int limit){
       return marketService.getAllListMarket(offset, limit);
    }

    //싱세 중고게시글 조회
    @GetMapping("/markets/{marketId}")
    public MarketDetailRes getDetailMarket(@PathVariable Long marketId) {
        return marketService.getDetailMarket(marketId);
    }

    //중고게시글 작성
    @PostMapping("/markets")
    public void creatMarketBoard(@RequestPart MarketPostReq postDto,
                                 @RequestPart("file") List<MultipartFile> multipartFiles,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        log.info("itemName = {}", postDto.getItemName());
        log.info("username={}", userDetails.getUsername());
        log.info("file name = {}", multipartFiles.size());
        marketService.creatMarketBoard(postDto, multipartFiles, userDetails);
    }

    //중고게시글 수정
    @PutMapping("/markets/{marketId}")
    public MarketBoard editMarketBoard(@PathVariable Long marketId,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @RequestPart MarketPostReq postReq,
                                       @RequestPart("file") List<MultipartFile> multipartFiles){
        return marketService.editMarketBoard(marketId, userDetails, postReq, multipartFiles);
    }

    //중고게시글 삭제
    @DeleteMapping("/markets/{marketId}")
    public void deleteMarketBoard(@PathVariable Long marketId,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        marketService.deleteMarketBoard(marketId, userDetails);
    }

}
