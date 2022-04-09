package com.example.demo.service;

import com.example.demo.dto.reponse.ComBoardDetailDto;
import com.example.demo.dto.reponse.ComBoardSimResDto;
import com.example.demo.dto.request.ComBoardPostDto;
import com.example.demo.model.CommunityBoard;
import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommunityService(CommunityRepository communityRepository, UserRepository userRepository) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
    }


    //커뮤니티 게시판 전체조회
    @Transactional(readOnly = true)
    public List<ComBoardSimResDto> getCommunityBoard() {
        List<CommunityBoard> simResDtos = communityRepository.findAllByOrderByCreatedAtDesc();
        return ComBoardSimResDto.list(simResDtos);
    }

    //커뮤니티 게시판 상세조회
    @Transactional(readOnly = true)
    public CommunityBoard getComBoardDetail(Long communityId) {
        CommunityBoard communityBoard = communityRepository.findById(communityId).orElseThrow(
                () -> new RuntimeException("게시글이 존재하지 않습니다")
        );
        return CommunityBoard.detail(communityBoard);

    }

    //커뮤니티 게시판 작성
    @Transactional
    public void postComBoard(ComBoardPostDto postDto) {
       User findUsername = userRepository.findByUsername(postDto.getUsername()).orElseThrow(
               ()-> new IllegalArgumentException("가입된 유저가 아닙니다.")
       );
       CommunityBoard communityBoard = new CommunityBoard();
       communityRepository.save(communityBoard.postComBoard(postDto));
    }

    //커뮤니티 게시판 수정
    @Transactional
    public Long editCommunityBoard(Long communityBoardId, ComBoardDetailDto detailDto) {
        CommunityBoard communityBoard = communityRepository.findById(communityBoardId).orElseThrow(
                ()-> new RuntimeException("게시글이 존재하지 않습니다")
        );
        communityBoard.editCommunityBoard(detailDto);
        return communityBoardId;
    }

    //커뮤니티 게시판 삭제
    @Transactional
    public void deleteComBoard(Long communityId) {
        CommunityBoard communityBoard = communityRepository.findById(communityId).orElseThrow(
                ()-> new RuntimeException("작성한 유저가 아닙니다")
        );
        communityRepository.delete(communityBoard);
    }


}
