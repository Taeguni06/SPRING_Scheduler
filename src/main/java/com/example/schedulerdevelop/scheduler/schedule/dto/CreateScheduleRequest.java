package com.example.schedulerdevelop.scheduler.schedule.dto;

import com.example.schedulerdevelop.scheduler.user.entity.User;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    private String title;
    private String content;
}
