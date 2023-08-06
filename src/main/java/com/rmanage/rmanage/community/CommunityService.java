package com.rmanage.rmanage.community;

import com.rmanage.rmanage.entity.Community;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.WorkPlace;
import com.rmanage.rmanage.community.CommunityDto;
import com.rmanage.rmanage.community.CommunityResponseDto;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;
    private EntityManager entityManager;

    @Autowired
    public CommunityService(CommunityRepository communityRepository, CommentRepository commentRepository, EntityManager entityManager){
        this.communityRepository = communityRepository;
        this.commentRepository = commentRepository;
        this.entityManager = entityManager;
    }

    public CommunityResponseDto getCommunityList(Long workplaceId, String type){
        try{
            List<Community> communities = communityRepository.findByWorkPlace_WorkPlaceIdAndType(workplaceId, type);
            List<CommunityDto> communityList = new ArrayList<>();
            for (Community community : communities) {
                LocalDateTime createdAt = LocalDateTime.now();
                communityList.add(new CommunityDto(community.getUser().getUserId(),community.getPostId(), createdAt, community.getWriter(), community.getTitle(), community.getRecommend(), community.getContent(), type));
            }
            return new CommunityResponseDto(true, 1011, "조회 성공", communityList);
        }
        catch(Exception e){
            System.out.println(e);
            return new CommunityResponseDto(false, 3011, "조회 실패", null);
        }
    }
    public CommunityResponseDto writeCommunity(Long workplaceId, String type, Long userId, String title, String content){
        /* 작성자 정보 가져오기
        User user = userRepository.findById(communityDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다. id=" + userId));
        */
        try{
            User user = entityManager.find(User.class, userId);
            WorkPlace workPlace = entityManager.find(WorkPlace.class, workplaceId);
            Community community = new Community(user, workPlace, type, title, content, user.getNickname(), false, 0);
            Community community1 = communityRepository.save(community);
            LocalDateTime createdAt = LocalDateTime.now();
            List<CommunityDto> communityDto = List.of(new CommunityDto(userId, community1.getPostId(), createdAt, community1.getWriter(), community1.getTitle(), 0, community1.getContent(), type));

            return new CommunityResponseDto(true, 1012, "글쓰기 성공", null);
        }
        catch (Exception e){
            System.out.println(e);
            return new CommunityResponseDto(false, 3012, "글쓰기 실패", null);
        }
    }
}
