package com.example.schedulerdevelop.scheduler.schedule.dto;

import com.example.schedulerdevelop.scheduler.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    @NotBlank
    @Size(max = 20, message = "일정 제목은 20자 이내입니다.")
    private String title;
    @NotBlank
    @Size(max = 200, message = "일정 내용은 200자 이내입니다.")
    private String content;
}
