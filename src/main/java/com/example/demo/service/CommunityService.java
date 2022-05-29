package com.example.demo.service;

import com.example.demo.dto.reponse.ComBoardDetailRes;
import com.example.demo.dto.reponse.ComBoardSimRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.ComBoardPostReq;
import com.example.demo.model.CommunityBoard;
import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.queryRepository.BoardQueryRepository;
import com.example.demo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;

    private final BoardQueryRepository queryRepository;
    private final UserRepository userRepository;


    //커뮤니티 게시판 전체조회
    @Transactional(readOnly = true)
    public Response getCommunityBoard(int offset, int limit) {
        List<CommunityBoard> communityBoards = queryRepository.findAllComBoard(offset, limit);
        List<ComBoardSimRes> simResDtos = new ArrayList<>();
        for (CommunityBoard communityBoard : communityBoards)
            simResDtos.add(new ComBoardSimRes(communityBoard));
        return new Response<>(simResDtos);
    }

    //커뮤니티 게시판 상세조회
    @Transactional(readOnly = true)
    public ComBoardDetailRes getComBoardDetail(Long communityId) {
        CommunityBoard communityBoard = communityRepository.findById(communityId).orElseThrow(
                () -> new RuntimeException("게시글이 존재하지 않습니다")
        );
        return new ComBoardDetailRes(communityBoard);
    }

    //커뮤니티 게시판 작성
    public void postComBoard(ComBoardPostReq postDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
       User findUser =  userRepository.findByUsername(user.getUsername()).orElseThrow(
               () -> new IllegalArgumentException("가입된 유저가 아닙니다.")
       );
       CommunityBoard communityBoard = new CommunityBoard(postDto, findUser);
       communityRepository.save(communityBoard);
    }

    //커뮤니티 게시판 수정
    public void editCommunityBoard(Long communityBoardId, ComBoardDetailRes detailDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        CommunityBoard communityBoard = communityRepository.findById(communityBoardId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않은 게시글입니다")
        );
        if (user.getUsername().equals(communityBoard.getUser().getUsername())){
            communityBoard.editCommunityBoard(detailDto);
        }else throw new RuntimeException("게시글 작성자가 아닙니다");
    }

    //커뮤니티 게시판 삭제
    public void deleteComBoard(Long communityId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        CommunityBoard communityBoard = communityRepository.findById(communityId).orElseThrow(
                () -> new RuntimeException("게시글이 존재하지 않습니다")
        );
        if (user.getUsername().equals(communityBoard.getUser().getUsername())){
            communityRepository.delete(communityBoard);
        }else throw new RuntimeException("게시글 작성자가 아닙니다");
    }
}
