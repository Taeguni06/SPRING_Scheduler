package com.example.schedulerdevelop.scheduler.user.controller;

import com.example.schedulerdevelop.global.customConst.SessionConst;
import com.example.schedulerdevelop.scheduler.user.dto.LoginRequest;
import com.example.schedulerdevelop.scheduler.user.dto.SignUpRequest;
import com.example.schedulerdevelop.scheduler.user.dto.UserResponse;
import com.example.schedulerdevelop.scheduler.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest
    ) {
        UserResponse response = userService.login(request);

        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(SessionConst.LOGIN_USER, response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return ResponseEntity.ok().build();
    }
}
