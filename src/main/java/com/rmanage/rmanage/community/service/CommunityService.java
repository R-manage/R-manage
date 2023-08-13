package com.rmanage.rmanage.community.service;

import com.rmanage.rmanage.community.dto.*;
import com.rmanage.rmanage.community.repository.CommentRepository;
import com.rmanage.rmanage.community.repository.CommunityRepository;
import com.rmanage.rmanage.document.config.s3Setting.S3Uploader;
import com.rmanage.rmanage.document.dto.ResponseDto;
import com.rmanage.rmanage.entity.Comment;
import com.rmanage.rmanage.entity.Community;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.WorkPlace;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;
    private EntityManager entityManager;
    private S3Uploader s3Uploader;
    private CommentService commentService;
    @Autowired
    public CommunityService(CommunityRepository communityRepository, CommentRepository commentRepository, EntityManager entityManager, S3Uploader s3Uploader){
        this.communityRepository = communityRepository;
        this.commentRepository = commentRepository;
        this.entityManager = entityManager;
        this.s3Uploader = s3Uploader;
    }
    // 글 목록 조회
    public CommunityListResponseDto getCommunityList(Long workplaceId, String type){
        try{
            // 근무지 찾기
            WorkPlace workPlace = entityManager.find(WorkPlace.class, workplaceId);
            if(workPlace == null){
                return new CommunityListResponseDto(false, 3003, "근무지 정보가 존재하지 않음", null);
            }
            List<Community> communities = communityRepository.findCommunityByWorkPlaceAndType(workPlace, type);
            if (communities.isEmpty()){
                return new CommunityListResponseDto(false, 3027, "게시글 없음", null);
            }

            List<CommunityListDto> communityList = new ArrayList<>();
            for (Community community : communities) {
                communityList.add(new CommunityListDto(community.getCreatedAt().toLocalDate(), community.getWriter(), community.getTitle(), community.getRecommend()));
            }
            return new CommunityListResponseDto(true, 1012, "게시글 목록 조회 성공", communityList);
        }
        catch(Exception e){
            System.out.println(e);
            return new CommunityListResponseDto(false, 3022, "게시글 목록 조회 실패", null);
        }
    }
    // 글 하나 조회 (댓글 조회도 함께 함)
    public CommunityResponseDto getCommunity(Long postId){
        try{
            //List<Community> communities = communityRepository.findCommunityBypostId(postId);
            Optional<Community> communities = communityRepository.findById(postId);
            List<Comment> comments = commentRepository.findCommentBypostId(postId);
            if (communities.isEmpty()) {
                return new CommunityResponseDto(false, 3027, " 게시글 없음", null);
            }
            if (communities.get().getUser().isEmployee() == false && communities.get().getType().equals("알바생 잡담")){
                return new CommunityResponseDto(false, 3039, "알바생 잡담 권한없음", null);
            }
            if (communities.get().getUser().isEmployee() == true && communities.get().getType().equals("공지사항")){
                return new CommunityResponseDto(false, 3038, "공지사항 권한없음", null);
            }
            Community community = communities.get();
            // List<CommunityDto> community = new ArrayList<>();
            List<CommentDto> comment = new ArrayList<>();
            for(Comment comment1 : comments){
                comment.add(new CommentDto(comment1.getCommentId(), postId, comment1.getUser().getUserId(), comment1.getContent(), comment1.isAnonymous()));
            }
            List<CommunityDto> communityResult = new ArrayList<>();
            communityResult.add(new CommunityDto(community.getUser().getUserId(), postId, community.getCreatedAt(), community.getWriter(), community.getTitle(), community.getRecommend(), community.getContent(), community.getImageUrl(), comment));

            /*for (Community community1 : communities){
                community.add(new CommunityDto(community1.getUser().getUserId(), postId, community1.getCreatedAt(), community1.getWriter(), community1.getTitle(), community1.getRecommend(), community1.getContent(), community1.getImageUrl(), comment));
            }*/

            return new CommunityResponseDto(true, 1011, "게시글 조회 성공", communityResult);
        }
        catch(Exception e){
            System.out.println(e);
            return new CommunityResponseDto(false, 3021, "게시글 조회 실패", null);
        }
    }
    // 글 쓰기
    public CommunitySaveResponseDto postCommunity(Long workplaceId, String type, Long userId, String title, String content, MultipartFile image){
        /* 작성자 정보 가져오기
        User user = userRepository.findById(communityDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다. id=" + userId));
        */
        try{
            User user = entityManager.find(User.class, userId);
            WorkPlace workPlace = entityManager.find(WorkPlace.class, workplaceId);
            // 이미지 업로드
            String filename = null;
            filename = s3Uploader.uploadFiles(image, "image");
            if(filename == null){
                return new CommunitySaveResponseDto(false,2020,"이미지 업로드에 실패함",null);
            }
            if(user == null){
                return new CommunitySaveResponseDto(false, 3002, "유저 정보 없음", null);
            }
            if(workPlace == null){
                return new CommunitySaveResponseDto(false, 3003, "근무지 정보가 존재하지 않음", null);
            }
            if(user.isEmployee() == true && type.equals("공지사항")){{
                    return new CommunitySaveResponseDto(false,3038,"공지사항 권한 없음", null);
                }
            }
            if(user.isEmployee() == false && type.equals("알바생 잡담")){{
                return new CommunitySaveResponseDto(false,3039,"알바생 잡담 권한 없음", null);
            }
            }
            if(title == null || title == "") {
                return new CommunitySaveResponseDto(false,2011,"제목 누락", null);
            }
            if(content == "" || content == null) {
                return new CommunitySaveResponseDto(false,2012, "내용 누락", null);
            }

            Community community = new Community(user, workPlace, type, title, content, user.getNickname(), false, 0, filename);
            Community community1 = communityRepository.save(community);
            List<CommunitySaveDto> communitySaveDto = List.of(new CommunitySaveDto(userId, community1.getPostId(), community1.getCreatedAt(), community1.getWriter(), community1.getTitle(), community1.getContent(), type, community1.getImageUrl()));

            return new CommunitySaveResponseDto(true, 1013, "글쓰기 성공", null);
        }
        catch (Exception e){
            System.out.println(e);
            return new CommunitySaveResponseDto(false, 3023, "글쓰기 실패", null);
        }
    }
    public CommunityResponseDto patchCommunity(Long postId, String title, String content, MultipartFile image) {
        try {
            String filename = null;
            filename = s3Uploader.uploadFiles(image, "image");
            if(filename == null){
                return new CommunityResponseDto(false,2030,"이미지 업로드에 실패함",null);
            }
            Optional<Community> community = communityRepository.findById(postId);
            if (community.isEmpty()) {
                return new CommunityResponseDto(false, 3027, " 게시글 없음", null);
            }
            if (community.get().getUser() == null){
                return new CommunityResponseDto(false, 3002, "유저 정보 없음", null);
            }
            if (community.get().getWorkPlace() == null){
                return new CommunityResponseDto(false, 3003, "근무지 정보 없음", null);
            }
            Community community1 = community.get();
            if (community1.getUser().isEmployee() == false && community1.getType().equals("알바생 잡담")){
                return new CommunityResponseDto(false, 3039, "알바생 잡담 권한없음", null);
            }
            if (title == null && content == null) {
                return new CommunityResponseDto(false, 2016, "수정 사항 없음", null);
            }
            // 수정
            community1.updateCommunity(title, content, filename);
            return new CommunityResponseDto(true, 1015, "글 수정 성공", null);
        } catch (Exception e) {
            System.out.println(e);
            return new CommunityResponseDto(false, 3024, "글 수정 실패", null);
        }
    }

    // 글 삭제
    public CommunityResponseDto deleteCommunity(Long postId){
        try {
            Optional<Community> community = communityRepository.findById(postId);
            if (community.isEmpty()) {
                return new CommunityResponseDto(false, 3014, " 게시글 없음", null);
            }
            if (community.get().getUser() == null) {
                return new CommunityResponseDto(false, 3012, "유저 정보 없음", null);
            }
            if (community.get().getWorkPlace() == null) {
                return new CommunityResponseDto(false, 3013, "근무지 정보 없음", null);
            }
            Community delCommunity = community.get();
            if (delCommunity.getUser().isEmployee() == false && delCommunity.getType().equals("알바생 잡담")){
                return new CommunityResponseDto(false, 3039, "알바생 잡담 권한없음", null);
            }
            if (delCommunity.getUser().isEmployee() == true && delCommunity.getType().equals("공지사항")){
                return new CommunityResponseDto(false, 3038, "공지사항 권한없음", null);
            }
            communityRepository.delete(delCommunity);
            boolean commentResult = commentService.deleteAllComment(postId);
            if (commentResult == false){
                return new CommunityResponseDto(false, 3030, "댓글 삭제 실패", null);
            }
            return new CommunityResponseDto(true,1016,"글 삭제 성공",null);

        }
        catch (Exception e){
            System.out.println(e);
            return new CommunityResponseDto(false, 3025, "글 삭제 실패", null);
        }
    }
}
