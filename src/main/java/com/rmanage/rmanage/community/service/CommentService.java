package com.rmanage.rmanage.community.service;

import com.rmanage.rmanage.community.dto.CommentDto;
import com.rmanage.rmanage.community.dto.CommentResponseDto;
import com.rmanage.rmanage.community.dto.CommunityResponseDto;
import com.rmanage.rmanage.community.repository.CommentRepository;
import com.rmanage.rmanage.community.repository.CommunityRepository;
import com.rmanage.rmanage.entity.Comment;
import com.rmanage.rmanage.entity.Community;
import com.rmanage.rmanage.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private EntityManager entityManager;
    private final CommunityRepository communityRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommunityRepository communityRepository, EntityManager entityManager) {
        this.commentRepository = commentRepository;
        this.communityRepository = communityRepository;
        this.entityManager = entityManager;
    }

    /*public CommentResponseDto getComment(Long postId){
        try{
            List<Comment> comments = commentRepository.findCommentBypostId(postId);
            List<CommentDto> commentList = new ArrayList<>();
            for (Comment co : comments) {
                LocalDateTime createdAt = LocalDateTime.now();
                commentList.add(new CommentDto(c
            }
            for (Comment comment : comments){
                commentList.add(new CommentDto(comment.get))

            }
            return new CommentResponseDto(true, 1011, "댓글 조회 성공", commentList);
        }
        catch(Exception e){
            System.out.println(e);
            return new CommunityResponseDto(false, 3011, "조회 실패", null);
        }
    }

     */

    public CommentResponseDto postComment(Long postId, String content, Long userId, boolean isAnonymous){
        try{
            User user = entityManager.find(User.class, userId);
            //Community community = entityManager.find(Community.class, postId);
            Optional<Community> community = communityRepository.findById(postId);
            if(user == null){
                return new CommentResponseDto(false, 3002, "사용자 정보 없음", null);
            }
            if(community.isEmpty()){
                return new CommentResponseDto(false, 3026, "댓글 작성할 게시글 없음", null);
            }
            if(user.isEmployee() == false && community.get().getType().equals("알바생 잡담")){
                if(user.isEmployee() == false){
                    return new CommentResponseDto(false, 3039, "알바생 잡담 권한없음", null);
                }
            }
            Community community1 = community.get();
            Comment comment = new Comment(user, community1, content, isAnonymous);
            Comment saveComment = commentRepository.save(comment);
            List<CommentDto> commentDto = List.of(new CommentDto(saveComment.getCommentId(), postId, saveComment.getUser().getUserId(), content, isAnonymous));
            return new CommentResponseDto(true, 1014, "댓글 작성 성공", null);
        }
        catch(Exception e){
            System.out.println(e);
            return new CommentResponseDto(false, 3029, "댓글 작성 실패", null);
        }
    }
    public CommentResponseDto deleteComment(Long postId, Long commentId){
        try{
            Optional<Community> community = communityRepository.findById(postId);
            Optional<Comment> comment = commentRepository.findById(commentId);
            if(community.isEmpty()){
                return new CommentResponseDto(false,3026,"게시글 없음",null);
            }
            if(comment.isEmpty()){
                return new CommentResponseDto(false,3028,"삭제할 댓글 없음",null);
            }
            if(community.get().getType().equals("알바생 잡담") && community.get().getUser().isEmployee() == false){
                return new CommentResponseDto(false, 3039, "알바생 잡담 권한없음", null);
            }
            Comment delcomment = comment.get();
            //System.out.println(delcomment);
            commentRepository.delete(delcomment);
            return new CommentResponseDto(true,1017,"댓글 삭제 성공",null);
        }
        catch(Exception e){
            System.out.println(e);
            return new CommentResponseDto(false,3030,"댓글 삭제 실패",null);
        }
    }

    // 글 삭제 시 연동해서 댓글 모두 삭제
    public boolean deleteAllComment(Long postId){
        try{

            Community community = entityManager.find(Community.class,postId);

            List<Comment> comments = commentRepository.findCommentByCommunity(community);
            // List<CommentDto> comment = new ArrayList<>();
            // if(comments.isEmpty()){ return false; }
            for(Comment comment1 : comments){
                commentRepository.delete(comment1);
                //comment.add(new CommentDto(comment1.getCommentId(), postId, comment1.getUser().getUserId(), comment1.getContent(), comment1.isAnonymous()));
            }
            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }


}
