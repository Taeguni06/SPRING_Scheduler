package com.example.schedulerdevelop.scheduler.comment.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {
    @Size(max = 100, message = "댓글은 100자 이내입니다.")
    private String title;
}
