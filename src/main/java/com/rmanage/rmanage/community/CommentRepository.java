package com.rmanage.rmanage.community;

import com.rmanage.rmanage.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
