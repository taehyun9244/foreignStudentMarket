package com.example.demo.service;

import com.example.demo.dto.reponse.MarketDetailRes;
import com.example.demo.dto.reponse.MarketSimRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.MarketPostReq;
import com.example.demo.model.MarketBoard;
import com.example.demo.model.UploadFile;
import com.example.demo.model.User;
import com.example.demo.repository.MarketRepository;
import com.example.demo.repository.UploadFileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.queryRepository.AllBoardQueryRepository;
import com.example.demo.repository.queryRepository.JpqlBoardQueryRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.util.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;
    private final UserRepository userRepository;
    private final UploadFileRepository uploadFileRepository;
    private final JpqlBoardQueryRepository queryRepository;
    private final FileStore fileStore;
    private final AllBoardQueryRepository allBoardQueryRepository;

    //전체 게시글 조회 Jpql
    @Transactional(readOnly = true)
    public Response getAllListMarket(int offset, int limit) {
        List<MarketBoard> marketBoards = queryRepository.findAllMarket(offset, limit);
        List<MarketSimRes> collect = marketBoards.stream()
                .map(marketBoard -> new MarketSimRes(marketBoard))
                .collect(Collectors.toList());
        return new Response(collect);
    }

    //전체 게시글 조회 QueryDsl -> dto
    @Transactional(readOnly = true)
    public Response getAllListMarketV2(Pageable pageable) {
        Page<MarketSimRes> marketBoards = allBoardQueryRepository.findMarketBoardAllDto(pageable);
        log.info("marketBoards ={}", marketBoards);
        return new Response(marketBoards);
    }


    //상세 게시글 조회 QueryDsl -> dto
    @Transactional(readOnly = true)
    public List<MarketDetailRes> getDetailMarketV2(Long marketId){
        List<MarketDetailRes> findById = allBoardQueryRepository.findByIdMarketBoardDto(marketId);
        log.info("findById={}", findById);
        return findById;
    }


    //게시글 작성
    @Transactional
    public void creatMarketBoard(MarketPostReq postDto, List<MultipartFile> multipartFiles, UserDetailsImpl userDetails) throws IOException {
        User user = userDetails.getUser();
        User writer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new RuntimeException("회원가입을 해주세요 가입되지 않았습니다")
        );
        List<UploadFile> uploadFiles = fileStore.saveFiles(multipartFiles);
        List<UploadFile> saveImages = uploadFileRepository.saveAll(uploadFiles);

        MarketBoard creatMarketBoard = new MarketBoard(postDto, writer, saveImages);
        marketRepository.save(creatMarketBoard);
    }


    //게시글 수정
    @Transactional
    public MarketBoard editMarketBoard(Long marketId, UserDetailsImpl userDetails, MarketPostReq postReq, List<MultipartFile> multipartFiles) throws IOException {
        User user = userDetails.getUser();
        User writer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new RuntimeException("회원가입을 해 주세요")
        );
        MarketBoard findBoard = marketRepository.findById(marketId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 게시글 입니다")
        );
        if (writer.equals(findBoard.getUser())) {
            List<UploadFile> uploadFiles = fileStore.saveFiles(multipartFiles);
            List<UploadFile> saveImages = uploadFileRepository.saveAll(uploadFiles);
            MarketBoard editMarketBoard = new MarketBoard(postReq, saveImages);
            marketRepository.save(editMarketBoard);
        }
        return new MarketBoard();
    }

    //게시글 삭제
    @Transactional
    public void deleteMarketBoard(Long marketId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        User writer = userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new RuntimeException("회원가입을 해 주세요")
        );
        MarketBoard findBoard = marketRepository.findById(marketId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 게시글 입니다")
        );
        if (writer.equals(findBoard.getUser())){
            marketRepository.delete(findBoard);
        }
    }
}
