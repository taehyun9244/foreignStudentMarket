package com.example.demo.service;

import com.example.demo.dto.reponse.DeliveryBoardDetailResDto;
import com.example.demo.dto.reponse.MarketDetailResDto;
import com.example.demo.dto.reponse.MarketSimResDto;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.MarketPostDto;
import com.example.demo.model.MarketBoard;
import com.example.demo.model.UploadFile;
import com.example.demo.model.User;
import com.example.demo.repository.MarketRepository;
import com.example.demo.repository.UploadFileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.queryRepository.BoardQueryRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.util.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;
    private final UserRepository userRepository;
    private final UploadFileRepository uploadFileRepository;
    private final BoardQueryRepository queryRepository;
    private final FileStore fileStore;

    //전체 게시글 조회
    public Response getAllListMarket(int offset, int limit) {
        List<MarketBoard> marketBoards = queryRepository.findAllMarket(offset, limit);
        List<MarketSimResDto> collect = marketBoards.stream()
                .map(marketBoard -> new MarketSimResDto(marketBoard))
                .collect(Collectors.toList());
        return new Response(collect);
    }

    //상세 게시글 조회
    public MarketDetailResDto getDetailMarket(Long marketId){
        MarketBoard marketBoard = marketRepository.findById(marketId).orElseThrow(
                ()-> new RuntimeException("존재하지 않는 게시글입니다")
        );
        List<UploadFile> findByImages = uploadFileRepository.findAllById(Collections.singleton(marketId));
        return new MarketDetailResDto(marketBoard, findByImages);
    }


    //게시글 작성
    public void creatMarketBoard(MarketPostDto postDto, List<MultipartFile> multipartFiles, UserDetailsImpl userDetails) throws IOException {
        User user = userDetails.getUser();
        User writer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new RuntimeException("회원가입을 해주세요 가입되지 않았습니다")
        );
        List<UploadFile> uploadFiles = fileStore.saveFiles(multipartFiles);
        List<UploadFile> imageFiles = uploadFileRepository.saveAll(uploadFiles);

        MarketBoard creatMarketBoard = new MarketBoard(postDto, writer, imageFiles);
        marketRepository.save(creatMarketBoard);
    }


}
