//package com.example.demo.controller;
//
//import com.example.demo.dto.reponse.Response;
//import com.example.demo.service.MarketService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class MarketController {
//
//    private final MarketService marketService;
//
//    //전체 중고게시글 조회
//    @GetMapping(path = "/marktes")
//    public Response getAllListMarket(@RequestParam(value ="offset", defaultValue = "0") int offset,
//                                  @RequestParam(value = "limit", defaultValue = "100")int limit){
//       return marketService.getAllListMarket(offset, limit);
//    }
//
//    //싱세 중고게시글 조회
//
//}
