package com.example.demo.service;

import com.example.demo.dto.reponse.ComCommentResDto;
import com.example.demo.dto.request.ComCommentPostReq;
import com.example.demo.model.CommunityBoard;
import com.example.demo.model.CommunityComment;
import com.example.demo.model.User;
import com.example.demo.repository.ComCommentRepository;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ComCommentService {
    private final ComCommentRepository comCommentRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    //커뮤니티 게시판의 댓글 조회
    @Transactional(readOnly = true)
    public List<ComCommentResDto> getComComment(Long communityBoardId) {
        List<CommunityComment>  communityComments = comCommentRepository.findAllByCommunityBoardIdOrderByCreatedAtDesc(communityBoardId);
        List<ComCommentResDto> resDtos = new ArrayList<>();
        for (CommunityComment communityComment : communityComments){
            resDtos.add(new ComCommentResDto(communityComment));
        }
        return resDtos;
    }

    //커뮤니티 게시판의 댓글 생성
    public void creatComComment(UserDetailsImpl userDetails, ComCommentPostReq postReq, Long communityBoardId) {
        User writer = userDetails.getUser();
        User findUser = userRepository.findByUsername(writer.getUsername()).orElseThrow(
                ()-> new RuntimeException("회원가입을 해 주세요")
        );
        CommunityBoard findCommunityBoard = communityRepository.findById(communityBoardId).orElseThrow(
                ()->new RuntimeException("게시글이 존재하지 않습니다")
        );
        List<CommunityComment> communityComments = comCommentRepository.findAllByCommunityBoardIdOrderByCreatedAtDesc(findCommunityBoard.getId());
        int count = communityComments.size();
        CommunityComment communityComment = new CommunityComment(findUser, postReq, findCommunityBoard);
        communityComment.getCommunityBoard().addComment(count);
        comCommentRepository.save(communityComment);
    }

    //커뮤니티 게시판의 댓글 삭제
    public void deleteComComment(UserDetailsImpl userDetails, Long comCommentId, Long communityBoardId) {
        User writer = userDetails.getUser();
        CommunityBoard communityBoard = communityRepository.findById(communityBoardId).orElseThrow(
                ()-> new RuntimeException("존재하지 않는 게시글입니다")
        );
        CommunityComment communityComment = comCommentRepository.findById(comCommentId).orElseThrow(
                ()-> new RuntimeException("존재하지 않은 댓글입니다")
        );
        if (writer.getUsername().equals(communityComment.getUser().getUsername())){
            List<CommunityComment> countComment = comCommentRepository.findAllByCommunityBoardIdOrderByCreatedAtDesc(communityBoardId);
            int deliCommentSize = countComment.size();
            communityBoard.removeComment(deliCommentSize);
            comCommentRepository.delete(communityComment);
        }else throw new RuntimeException("댓글 작성자가 아닙니다");
    }
}
