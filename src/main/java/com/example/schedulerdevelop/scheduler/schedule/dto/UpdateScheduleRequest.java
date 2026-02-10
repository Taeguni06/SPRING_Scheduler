package com.example.schedulerdevelop.scheduler.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {
    @NotBlank
    @Size(max = 20, message = "일정 제목은 20자 이내입니다.")
    private String title;
}
