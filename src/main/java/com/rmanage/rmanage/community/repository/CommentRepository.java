package com.rmanage.rmanage.community.repository;

import com.rmanage.rmanage.entity.Comment;
import com.rmanage.rmanage.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByCommunity(Community community);
}
