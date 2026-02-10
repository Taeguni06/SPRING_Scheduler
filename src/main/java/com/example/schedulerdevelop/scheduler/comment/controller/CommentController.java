package com.example.schedulerdevelop.scheduler.comment.controller;

import com.example.schedulerdevelop.scheduler.comment.dto.CommentResponse;
import com.example.schedulerdevelop.scheduler.comment.dto.CreateCommentRequest;
import com.example.schedulerdevelop.scheduler.comment.service.CommentService;
import com.example.schedulerdevelop.scheduler.user.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules/{scheduleId}/comments")
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserResponse loginUser = (UserResponse) session.getAttribute("LOGIN_USER");

        return ResponseEntity.status(HttpStatus.OK).body(commentService.save(scheduleId, request, loginUser.getId()));
    }
}
