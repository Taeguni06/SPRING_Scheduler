package com.example.schedulerdevelop.scheduler.user.dto;

import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private String name;
    private String email;
    private String password;
}
