package com.example.schedulerdevelop.scheduler.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetScheduleResponse {
    private final String title;
    private final String content;
    private final Long commentCnt;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String userName;

    public GetScheduleResponse(String title, String content, Long commentCnt, LocalDateTime createdAt, LocalDateTime modifiedAt, String userName) {
        this.title = title;
        this.content = content;
        this.commentCnt = commentCnt;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userName = userName;
    }
}
