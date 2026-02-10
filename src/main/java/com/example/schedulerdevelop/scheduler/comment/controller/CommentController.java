package com.example.schedulerdevelop.scheduler.comment.controller;

import com.example.schedulerdevelop.global.exception.UnauthorizedException;
import com.example.schedulerdevelop.scheduler.comment.dto.CommentResponse;
import com.example.schedulerdevelop.scheduler.comment.dto.CreateCommentRequest;
import com.example.schedulerdevelop.scheduler.comment.dto.UpdateCommentRequest;
import com.example.schedulerdevelop.scheduler.comment.service.CommentService;
import com.example.schedulerdevelop.scheduler.user.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long scheduleId,
            HttpServletRequest httpRequest,
            @RequestBody CreateCommentRequest request
    ) {
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        UserResponse loginUser = (UserResponse) session.getAttribute("LOGIN_USER");

        return ResponseEntity.status(HttpStatus.OK).body(commentService.save(scheduleId, request, loginUser.getId()));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> findOne(@PathVariable Long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findOne(commentId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> update(
            @PathVariable Long commentId,
            HttpServletRequest httpRequest,
            @RequestBody UpdateCommentRequest request
    ) {
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        UserResponse loginUser = (UserResponse) session.getAttribute("LOGIN_USER");

        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(commentId, request, loginUser.getId()));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long commentId,
            HttpServletRequest httpRequest
    ) {
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        UserResponse loginUser = (UserResponse) session.getAttribute("LOGIN_USER");

        commentService.delete(commentId, loginUser.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
