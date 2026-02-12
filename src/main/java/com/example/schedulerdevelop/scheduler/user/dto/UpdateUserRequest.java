package com.example.schedulerdevelop.scheduler.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @Size(max = 10, message = "이름은 10자 이하입니다.")
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @NotBlank @Size(min = 8, message = "비밀번호는 8자 이상입니다.")
    private String password;
}
