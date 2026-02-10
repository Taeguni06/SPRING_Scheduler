package com.example.schedulerdevelop.scheduler.comment.repository;

import com.example.schedulerdevelop.scheduler.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
